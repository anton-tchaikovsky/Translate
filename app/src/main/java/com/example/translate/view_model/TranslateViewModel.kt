package com.example.translate.view_model

import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.example.translate.interactor.ITranslateInteractor
import com.example.translate.model.data.AppState
import com.example.translate.model.data.TranslateEntity
import com.example.translate.model.data.dto.DataModel
import com.example.translate.unit.mapFromDataModelItemToTranslateEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TranslateViewModel(
    private val translateInteractor: ITranslateInteractor<DataModel>,
    private val handle: SavedStateHandle
) :
    BaseTranslateViewModel<AppState>() {

    override var isOnline: Boolean = false

    private val scope = CoroutineScope(Dispatchers.IO)

    private val querySearchInputWordStateFlow = MutableStateFlow("")

    private val networkCallback: NetworkCallback = object : NetworkCallback() {

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            isOnline = true
        }

        override fun onUnavailable() {
            super.onUnavailable()
            isOnline = false
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            isOnline = false
        }
    }

    init {
        translateInteractor.registerNetworkCallback(networkCallback)
        setupSearchInputWordStateFlow()
    }

    override fun onCleared() {
        super.onCleared()
        translateInteractor.unregisterNetworkCallback(networkCallback)
        scope.coroutineContext.cancelChildren()
    }

    override fun onSearchWord(text: String?) {
        cancelJob()
        if (!text.isNullOrEmpty()) {
            if (isOnline) {
                onLoadingDataModel()
                viewModelCoroutineScope.launch {
                    startLoadingDataModel(text)
                }
            } else
                onEmptyDataModel(DISCONNECT_NETWORK)
        } else
            onEmptySearchText()
    }

    override fun onChangingInputWord(inputWord: String?) {
        inputWord?.let {
            querySearchInputWordStateFlow.value = it
        }
    }

    override fun onInitView() {
        handle.get<List<TranslateEntity>>(KEY_HANDLE_TRANSLATE)?.let {
            translateLiveData.value = AppState.Success(it)
        }
    }

    override fun getTranslateLiveData(): LiveData<AppState> = translateLiveData

    override fun getSingleEventLiveData(): LiveData<AppState> = singleEventLiveData

    override fun handleError(error: Throwable) {
        onErrorLoadingDataModel(error)
    }

    private fun onEmptySearchText() {
        handle.remove<List<TranslateEntity>>(KEY_HANDLE_TRANSLATE)
        singleEventLiveData.value = AppState.Info(EMPTY_SEARCH_TEXT)
        translateLiveData.value = AppState.EmptyData
    }

    private fun onEmptyDataModel(info: String) {
        handle.remove<List<TranslateEntity>>(KEY_HANDLE_TRANSLATE)
        singleEventLiveData.postValue(AppState.Info(info))
        translateLiveData.postValue(AppState.EmptyData)
    }

    private fun onCorrectDataModel(listTranslateEntity: List<TranslateEntity>) {
        handle[KEY_HANDLE_TRANSLATE] = listTranslateEntity
        translateLiveData.postValue(AppState.Success(listTranslateEntity))
    }

    private fun onErrorLoadingDataModel(error: Throwable) {
        handle.remove<List<TranslateEntity>>(KEY_HANDLE_TRANSLATE)
        singleEventLiveData.postValue(AppState.Error(error))
        translateLiveData.postValue(AppState.EmptyData)
    }

    private fun onLoadingDataModel() {
        translateLiveData.value = AppState.Loading
    }

    private suspend fun startLoadingDataModel(text: String) {
        withContext(Dispatchers.IO) {
            translateInteractor.getDataModel(text).map {
                mapFromDataModelItemToTranslateEntity(it)
            }.also {
                if (it.isEmpty())
                    onEmptyDataModel(EMPTY_DATA_MODEL)
                else
                    onCorrectDataModel(it)
            }
        }
    }

    private suspend fun startLoadingInputWord(inputWord: String) {
        withContext(Dispatchers.IO) {
            translateInteractor.getDataModel(inputWord).map {
                mapFromDataModelItemToTranslateEntity(it)
            }.also {translateEntity ->
                if(isOnline){
                    translateLiveData.postValue(AppState.InputWords(translateEntity.map { it.text }))
                }
            }
        }
    }

    @OptIn(FlowPreview::class)
    private fun setupSearchInputWordStateFlow() {
        scope.launch {
            querySearchInputWordStateFlow
                .debounce(STATE_FLOW_TIMEOUT)
                .filter {
                    if (it.isEmpty()){
                        translateLiveData.postValue(AppState.InputWords(listOf()))
                        return@filter false
                    } else {
                        return@filter true
                    }
                }
                .distinctUntilChanged()
                .collectLatest{
                    viewModelCoroutineScope.launch {
                        startLoadingInputWord(it)
                    }
                }
        }
    }

    companion object {
        private const val EMPTY_DATA_MODEL = "Перевод не найден"
        private const val EMPTY_SEARCH_TEXT = "Введите слово для перевода"
        private const val KEY_HANDLE_TRANSLATE = "KeyHandleTranslate"
        private const val DISCONNECT_NETWORK = "Отсутствует подключение к сети"
        private const val STATE_FLOW_TIMEOUT = 500L
    }
}
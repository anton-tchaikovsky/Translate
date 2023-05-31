package com.example.translate.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.example.translate.interactor.ITranslateInteractor
import com.example.translate.model.data.AppState
import com.example.translate.model.data.TranslateEntity
import com.example.translate.model.data.dto.DataModel
import com.example.translate.utils.mapFromDataModelItemToTranslateEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TranslateViewModel(
    private val translateInteractor: ITranslateInteractor<DataModel>,
    private val handle: SavedStateHandle
) :
    BaseTranslateViewModel<AppState>() {

    override var isOnline: Boolean = false

    private val querySearchInputWordStateFlow = MutableStateFlow("")

    private var searchJob: Job? = null

    private var searchInputWordJob: Job? = null

    private var loadingInputWordJob: Job? = null

    init {
        setupGettingNetworkState()
        setupSearchInputWordStateFlow()
    }

    override fun onCleared() {
        super.onCleared()
        translateInteractor.unregisterNetworkCallback()
    }

    override fun onSearchWord(text: String?) {
        searchJob?.cancel()
        searchInputWordJob?.cancel()
        loadingInputWordJob?.cancel()
        if (!text.isNullOrEmpty()) {
            if (isOnline) {
                onLoadingDataModel()
                searchJob = viewModelCoroutineScope.launch {
                    startLoadingDataModel(text)
                }
            } else
                onEmptyDataModel(DISCONNECT_NETWORK)
        } else
            onEmptySearchText()
    }

    override fun onChangingInputWord(inputWord: String?) {
        searchInputWordJob?.let {
            if (it.isCancelled)
                setupSearchInputWordStateFlow()
        }
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
        translateInteractor.getDataModel(text)
            .map { dataModel ->
                dataModel.map { mapFromDataModelItemToTranslateEntity(it) }
            }
            .collect {
                if (it.isEmpty())
                    onEmptyDataModel(EMPTY_DATA_MODEL)
                else
                    onCorrectDataModel(it)
            }
    }

    private suspend fun startLoadingInputWord(inputWord: String) {
        translateInteractor.getDataModel(inputWord)
            .map { dataModel ->
                dataModel.map { mapFromDataModelItemToTranslateEntity(it) }
            }
            .collect {translateEntity ->
                translateLiveData.postValue(AppState.InputWords(translateEntity.map { it.text }))
            }
        }

    @OptIn(FlowPreview::class)
    private fun setupSearchInputWordStateFlow() {
        searchInputWordJob = viewModelCoroutineScope.launch {
            querySearchInputWordStateFlow
                .debounce(STATE_FLOW_TIMEOUT)
                .filter {
                    if (it.isEmpty()) {
                        translateLiveData.postValue(AppState.InputWords(listOf()))
                        return@filter false
                    } else {
                        return@filter true
                    }
                }
                .distinctUntilChanged()
                .collectLatest {
                    loadingInputWordJob = viewModelCoroutineScope.launch {
                        if (isOnline)
                            startLoadingInputWord(it)
                        else
                            translateLiveData.postValue(AppState.InputWords(listOf()))
                    }
                }
        }
    }

    private fun setupGettingNetworkState() {
        viewModelCoroutineScope.launch {
            withContext(Dispatchers.IO) {
                translateInteractor.registerNetworkCallback()
                    .collect {
                        isOnline = it
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
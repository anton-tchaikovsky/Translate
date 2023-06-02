package com.example.translate.view_model.translate_view_model

import androidx.lifecycle.SavedStateHandle
import com.example.translate.interactor.ITranslateInteractor
import com.example.translate.model.data.TranslateEntity
import com.example.translate.model.data.app_state.AppState
import com.example.translate.model.data.dto.DataModel
import com.example.translate.model.room.RoomTranslateEntity
import com.example.translate.utils.mapFromDataModelItemToTranslateEntity
import com.example.translate.utils.mapFromRoomTranslateEntityToTranslateEntity
import com.example.translate.utils.mapFromTranslateEntityToRoomTranslateEntity
import com.example.translate.view_model.BaseTranslateViewModel
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
) : BaseTranslateViewModel(),
    ITranslateViewModel {

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
            onLoadingData()
            searchJob = viewModelCoroutineScope.launch {
                startLoadingData(text)
            }
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

    override fun insertListRoomTranslateEntity(listRoomTranslateEntity: List<RoomTranslateEntity>) {
        viewModelCoroutineScope.launch {
            withContext(Dispatchers.IO) {
                translateInteractor.insertListRoomTranslateEntity(listRoomTranslateEntity)
            }
        }
    }

    override fun onInitView() {
        handle.get<List<TranslateEntity>>(KEY_HANDLE_TRANSLATE)?.let {
            translateLiveData.value = AppState.Success(it)
        }
    }

    private fun onEmptySearchText() {
        handle.remove<List<TranslateEntity>>(KEY_HANDLE_TRANSLATE)
        singleEventLiveData.value = AppState.Info(EMPTY_SEARCH_TEXT)
        translateLiveData.value = AppState.EmptyData
    }

    override fun onEmptyData(info: String) {
        handle.remove<List<TranslateEntity>>(KEY_HANDLE_TRANSLATE)
        super.onEmptyData(info)
    }

    override fun onCorrectData(listTranslateEntity: List<TranslateEntity>) {
        handle[KEY_HANDLE_TRANSLATE] = listTranslateEntity
        insertListRoomTranslateEntity(listTranslateEntity.map {
            mapFromTranslateEntityToRoomTranslateEntity(
                it
            )
        })
        super.onCorrectData(listTranslateEntity)
    }

    override fun onErrorLoadingData(error: Throwable) {
        handle.remove<List<TranslateEntity>>(KEY_HANDLE_TRANSLATE)
        super.onErrorLoadingData(error)
    }

    private suspend fun startLoadingData(text: String) {
        if (isOnline)
            startLoadingDataFromRemoteDataSourse(text)
        else
            startLoadingDataFromLocalDataSourse(text)
    }

    private suspend fun startLoadingDataFromLocalDataSourse(text: String) {
        withContext(Dispatchers.IO) {
            translateInteractor.readListRoomTranslateEntity(text).map {
                mapFromRoomTranslateEntityToTranslateEntity(it)
            }.let {
                if (it.isEmpty())
                    onEmptyData(EMPTY_DATA_FROM_LOCAL_DATA_SOURCE)
                else
                    onCorrectData(it)
            }
        }
    }

    private suspend fun startLoadingDataFromRemoteDataSourse(text: String) {
        translateInteractor.getDataModel(text)
            .map { dataModel ->
                dataModel.map { mapFromDataModelItemToTranslateEntity(it) }
            }
            .collect {
                if (it.isEmpty())
                    onEmptyData(EMPTY_DATA_FROM_REMOTE_DATA_SOURCE)
                else
                    onCorrectData(it)
            }
    }


    private suspend fun startLoadingInputWordFromRemoteDataSource(inputWord: String) {
        translateInteractor.getDataModel(inputWord)
            .map { dataModel ->
                dataModel.map { mapFromDataModelItemToTranslateEntity(it) }
            }
            .collect { listTranslateEntity ->
                translateLiveData.postValue(AppState.InputWords(listTranslateEntity.map { it.text }))
            }
    }

    private suspend fun startLoadingInputWordFromLocalDataSource(inputWord: String) {
        withContext(Dispatchers.IO) {
            translateInteractor.readListRoomTranslateEntity(inputWord).map {
                mapFromRoomTranslateEntityToTranslateEntity(it)
            }.let {listTranslateEntity ->
                translateLiveData.postValue(AppState.InputWords(listTranslateEntity.map { it.text }))
            }
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
                            startLoadingInputWordFromRemoteDataSource(it)
                        else
                            startLoadingInputWordFromLocalDataSource(it)
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
        private const val EMPTY_SEARCH_TEXT = "Введите слово для перевода"
        private const val EMPTY_DATA_FROM_REMOTE_DATA_SOURCE = "Перевод не найден"
        private const val EMPTY_DATA_FROM_LOCAL_DATA_SOURCE =
            "Перевод не найден. Подключите интернет и повторите попытку поиска"
        private const val KEY_HANDLE_TRANSLATE = "KeyHandleTranslate"
        private const val STATE_FLOW_TIMEOUT = 500L
    }
}

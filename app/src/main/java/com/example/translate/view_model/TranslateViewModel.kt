package com.example.translate.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import com.example.translate.interactor.ITranslateInteractor
import com.example.translate.model.data.AppState
import com.example.translate.model.data.TranslateEntity
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class TranslateViewModel @Inject constructor(
    private val translteInteractor: ITranslateInteractor<AppState>,
    private val handle: SavedStateHandle
) :
    BaseTranslateViewModel<AppState>() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
    }

    override fun onSearchWord(text: String?) {
        if (!text.isNullOrEmpty())
            subscribeToLoadingDataModel(text)
        else
            onEmptySearchText()
    }

    override fun onInitView() {
        handle.get<List<TranslateEntity>>(KEY_HANDLE_TRANSLATE)?.let{
            translateLiveData.value = AppState.Success(it)
        }
    }

    override fun getTranslateLiveData(): LiveData<AppState> = translateLiveData

    override fun getSingleEventLiveData(): LiveData<AppState> = singleEventLiveData

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

    private fun subscribeToLoadingDataModel(text: String) {
        compositeDisposable.add(
            translteInteractor.getDataModel(text)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    onLoadingDataModel()
                }
                .subscribeBy(
                    onSuccess = {
                        if (it is AppState.Success) {
                            val listTranslateEntity = it.listTranslateEntity
                            if (listTranslateEntity.isEmpty())
                                onEmptyDataModel(EMPTY_DATA_MODEL)
                            else
                                onCorrectDataModel(listTranslateEntity)
                        } else if (it is AppState.Info)
                            onEmptyDataModel(it.info)
                    },
                    onError = {
                        onErrorLoadingDataModel(it)
                    }
                )
        )
    }

    companion object {
        private const val EMPTY_DATA_MODEL = "Перевод не найден"
        private const val EMPTY_SEARCH_TEXT = "Введите слово для перевода"
        private const val KEY_HANDLE_TRANSLATE = "KeyHandleTranslate"
    }

}
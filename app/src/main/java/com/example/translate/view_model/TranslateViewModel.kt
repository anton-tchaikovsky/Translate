package com.example.translate.view_model

import androidx.lifecycle.LiveData
import com.example.translate.interactor.ITranslateInteractor
import com.example.translate.model.data.AppState
import com.example.translate.model.data.TranslateEntity
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class TranslateViewModel @Inject constructor(private val translteInteractor: ITranslateInteractor<AppState>) :
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

    override fun getTranslateLiveData(): LiveData<AppState> = translateLiveData

    override fun getSingleEventLiveData(): LiveData<AppState> = singleEventLiveData

    private fun onEmptySearchText() {
        singleEventLiveData.value = AppState.Info(EMPTY_SEARCH_TEXT)
        translateLiveData.value = AppState.EmptyData
    }

    private fun onEmptyDataModel(info: String) {
        singleEventLiveData.postValue(AppState.Info(info))
        translateLiveData.postValue(AppState.EmptyData)
    }

    private fun onCorrectDataModel(listTranslateEntity: List<TranslateEntity>) {
        translateLiveData.postValue(AppState.Success(listTranslateEntity))
    }

    private fun onErrorLoadingDataModel(error: Throwable) {
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
                        if (it is AppState.Success){
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
    }

}
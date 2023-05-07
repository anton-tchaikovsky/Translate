package com.example.translate.view_model

import androidx.lifecycle.LiveData
import com.example.translate.interactor.ITranslateInteractor
import com.example.translate.interactor.TranslateInteractor
import com.example.translate.model.data.AppState
import com.example.translate.model.data.TranslateEntity
import com.example.translate.model.data_sourse.api.RemoteDataSourse
import com.example.translate.model.repository.Repository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class TranslateViewModel :
    BaseTranslateViewModel<AppState>() {

    private val interactor: ITranslateInteractor<AppState> = TranslateInteractor(
        Repository(
            RemoteDataSourse()
        )
    )

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

    private fun onEmptySearchText() {
        translateLiveData.value = AppState.Info(EMPTY_SEARCH_TEXT)
    }

    private fun onEmptyDataModel() {
        translateLiveData.postValue(AppState.Info(EMPTY_DATA_MODEL))
    }

    private fun onCorrectDataModel(listTranslateEntity: List<TranslateEntity>) {
        translateLiveData.postValue(AppState.Success(listTranslateEntity))
    }

    private fun onErrorLoadingDataModel(error: Throwable) {
        translateLiveData.postValue(AppState.Error(error))
    }

    private fun onLoadingDataModel() {
        translateLiveData.value = AppState.Loading
    }

    private fun subscribeToLoadingDataModel(text: String) {
        compositeDisposable.add(
            interactor.getDataModel(text)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                    onLoadingDataModel()
                }
                .subscribeBy(
                    onSuccess = {
                        val listTranslateEntity = (it as AppState.Success).listTranslateEntity
                        if (listTranslateEntity.isEmpty())
                            onEmptyDataModel()
                        else
                            onCorrectDataModel(listTranslateEntity)
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
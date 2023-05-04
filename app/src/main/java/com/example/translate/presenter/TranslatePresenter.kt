package com.example.translate.presenter

import com.example.translate.model.data.AppState
import com.example.translate.model.data.dto.DataModel
import com.example.translate.model.data_sourse.api.RemoteDataSourse
import com.example.translate.model.repository.Repository
import com.example.translate.presenter.translate_recycle_view.IItemTranslatePresenter
import com.example.translate.presenter.translate_recycle_view.ItemTranslatePresenter
import com.example.translate.unit.mapFromDataModelItemToTranslateEntity
import com.example.translate.view.ITranslateView
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class TranslatePresenter<V : ITranslateView, T : AppState>(
    private val interactor: ITranslateInteractor<AppState> = TranslateInteractor(
        Repository(
            RemoteDataSourse()
        )
    ),
    private val mainThreadScheduler: Scheduler
) : ITranslatePresenter<V, T> {

    private var view: ITranslateView? = null

    override val itemTranslatePresenter: IItemTranslatePresenter = ItemTranslatePresenter()

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    private var dataModel:DataModel = DataModel()

    override fun attachView(view: V) {
        if (this.view != view)
            this.view = view.also {
                it.initView()
                it.renderData(AppState.Success(dataModel))
            }
    }

    override fun detachView() {
        compositeDisposable.clear()
        if (this.view == view)
            this.view = null
    }

    override fun onSearchWord(text: String?) {
        if (!text.isNullOrEmpty())
            subscribeToLoadingDataModel(text)
        else
            onEmptySearchText()
    }

    private fun onEmptySearchText() {
        dataModel.clear()
        view?.run {
            itemTranslatePresenter.entityList.apply {
                clear()
            }
            renderData(AppState.Info(EMPTY_SEARCH_TEXT))
        }
    }

    private fun onEmptyDataModel() {
        view?.run {
            itemTranslatePresenter.entityList.apply {
                clear()
            }
            renderData(AppState.Info(EMPTY_DATA_MODEL))
        }
    }

    private fun onCorrectDataModel(dataModel: DataModel) {
        view?.run {
            itemTranslatePresenter.entityList.apply {
                clear()
                addAll(dataModel.map {
                    mapFromDataModelItemToTranslateEntity(it)
                })
            }
            renderData(AppState.Success(dataModel))
        }
    }

    private fun onErrorLoadingDataModel(error: Throwable) {
        dataModel.clear()
        view?.run {
            itemTranslatePresenter.entityList.apply {
                clear()
            }
            renderData(AppState.Error(error))
        }
    }

    private fun subscribeToLoadingDataModel(text: String) {
        compositeDisposable.add(
            interactor.getDataModel(text)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThreadScheduler)
                .doOnSubscribe {
                    view?.renderData(AppState.Loading)
                }
                .subscribeBy(
                    onSuccess = {
                        dataModel = (it as AppState.Success).dataModel
                        if (dataModel.isEmpty())
                            onEmptyDataModel()
                        else
                            onCorrectDataModel(dataModel)
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
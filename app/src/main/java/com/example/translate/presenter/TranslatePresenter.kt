package com.example.translate.presenter

import com.example.translate.model.data.AppState
import com.example.translate.model.data_sourse.api.RemoteDataSourse
import com.example.translate.model.repository.Repository
import com.example.translate.presenter.translate_recycle_view.IItemTranslatePresenter
import com.example.translate.presenter.translate_recycle_view.ItemTranslatePresenter
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

    override fun attachView(view: V) {
        if (this.view != view)
            this.view = view.also {
                it.initView()
            }
    }

    override fun detachView() {
        compositeDisposable.clear()
        if (this.view == view)
            this.view = null
    }

    override fun onSearchDialog() {
        view?.showSearchDialog()
    }

    override fun onSearchWord(text: String?) {
        view?.hideSearchDialog()
        if (!text.isNullOrEmpty())
            compositeDisposable.add(
                interactor.getDataModel(text)
                    .subscribeOn(Schedulers.io())
                    .observeOn(mainThreadScheduler)
                    .doOnSubscribe {
                        view?.renderData(AppState.Loading)
                    }
                    .subscribeBy(
                        onSuccess = {
                            view?.run {
                                itemTranslatePresenter.entityList.apply {
                                    clear()
                                    addAll((it as AppState.Success).dataModel[0].meanings)
                                }
                                renderData(it)
                            }
                        },
                        onError = {
                            view?.run {
                                renderData(AppState.Error(it))
                            }
                        }
                    )
            )
    }

}
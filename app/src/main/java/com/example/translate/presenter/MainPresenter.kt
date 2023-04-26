package com.example.translate.presenter

import com.example.translate.model.data.AppState
import com.example.translate.model.data_sourse.api.RemoteDataSourse
import com.example.translate.model.repository.Repository
import com.example.translate.view.base.IMainView
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class MainPresenter <V: IMainView, T: AppState>(
     private val interactor: IMainInteractor<AppState> = MainInteractor(
        Repository(
            RemoteDataSourse()
        )
    ),
     private val mainThreadScheduler: Scheduler
) : IMainPresenter<V, T> {

    private var view: IMainView? = null

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun attachView(view: V) {
        if (this.view!=view)
            this.view = view
    }

    override fun detachView() {
        compositeDisposable.clear()
        if (this.view==view)
            this.view = null
    }

    override fun onSearchWord(word: String) {
        compositeDisposable.add(
            interactor.getDataModel(word)
                .subscribeOn(Schedulers.io())
                .observeOn(mainThreadScheduler)
                .doOnSubscribe {
                    view?.renderData(AppState.Loading)
                }
                .subscribeBy(
                    onSuccess = {
                        view?.renderData(it)
                    },
                    onError = {
                        view?.renderData(AppState.Error(it))
                    }
                )
        )
    }

}
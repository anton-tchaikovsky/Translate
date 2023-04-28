package com.example.translate.presenter

import com.example.translate.model.data.AppState
import com.example.translate.presenter.translate_recycle_view.IItemTranslatePresenter
import com.example.translate.view.ITranslateView

interface ITranslatePresenter<V: ITranslateView, T: AppState> {

    val itemTranslatePresenter: IItemTranslatePresenter

    fun attachView (view: V)

    fun detachView()

    fun onSearchDialog()

    fun onSearchWord(text: String?)

}
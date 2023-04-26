package com.example.translate.presenter

import com.example.translate.model.data.AppState
import com.example.translate.view.base.IMainView

interface IMainPresenter<V: IMainView, T: AppState> {

    fun attachView (view: V)

    fun detachView()

    fun onSearchWord(word: String)

}
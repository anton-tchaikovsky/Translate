package com.example.translate.view

import com.example.translate.model.data.AppState

interface ITranslateView {

    fun initView()

    fun showSearchDialog()

    fun hideSearchDialog()

    fun renderData(appState: AppState)

}
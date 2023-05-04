package com.example.translate.view

import com.example.translate.model.data.AppState

interface ITranslateView {

    fun initView()

    fun renderData(appState: AppState)

}
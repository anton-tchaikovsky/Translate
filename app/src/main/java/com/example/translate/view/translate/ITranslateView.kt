package com.example.translate.view.translate

import com.example.translate.model.data.AppState

interface ITranslateView <T: AppState>{

    fun initView()

    fun renderData(appState: T)

}
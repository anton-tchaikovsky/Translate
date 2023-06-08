package com.example.core.view_model

import com.example.model.data.TranslateEntity

interface IDataLoader {

    fun onLoadingData()

    fun onErrorLoadingData(error: Throwable)

    fun onEmptyData(info: String)

    fun onCorrectData(listTranslateEntity: List<TranslateEntity>)

}
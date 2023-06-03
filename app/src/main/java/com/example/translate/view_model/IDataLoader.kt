package com.example.translate.view_model

import com.example.translate.model.data.TranslateEntity

interface IDataLoader {

    fun onLoadingData()

    fun onErrorLoadingData(error: Throwable)

    fun onEmptyData(info: String)

    fun onCorrectData(listTranslateEntity: List<TranslateEntity>)

    fun onChangingFavoritesState(translateEntity: TranslateEntity)

}
package com.example.translate.view_model

interface IDataLoader <T> {

    fun onLoadingData()

    fun onErrorLoadingData(error: Throwable)

    fun onEmptyData(info: String)

    fun onCorrectData(listTranslateEntity: List<T>)

}
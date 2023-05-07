package com.example.translate.model.data

sealed class AppState{

    class Success (val listTranslateEntity: List<TranslateEntity> = listOf()): AppState()

    class Error (val error: Throwable): AppState()

    class Info (val info: String): AppState()

    object Loading: AppState()

    object EmptyData: AppState()

}

package com.example.translate.model.data

import com.example.translate.model.data.dto.DataModel

sealed class AppState{

    class Success (val dataModel: DataModel): AppState()

    class Error (val error: Throwable): AppState()

    object Loading: AppState()

}

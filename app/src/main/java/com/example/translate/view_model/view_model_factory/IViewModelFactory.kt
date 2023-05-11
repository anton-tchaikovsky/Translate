package com.example.translate.view_model.view_model_factory

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

interface IViewModelFactory <V: ViewModel>{

    fun create (handle: SavedStateHandle): V

}
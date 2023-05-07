package com.example.translate.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.translate.model.data.AppState
import com.example.translate.view_model.BaseTranslateViewModel

abstract class BaseTranslateActivity <T: AppState>: AppCompatActivity(), ITranslateView <T> {

    protected abstract val translateViewModel:BaseTranslateViewModel<T>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        translateViewModel.apply {
            getTranslateLiveData().observe(this@BaseTranslateActivity){
                renderData(it)
            }
            getSingleEventLiveData().observe(this@BaseTranslateActivity){
                renderData(it)
            }
        }
    }

}
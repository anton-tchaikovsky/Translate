package com.example.translate.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.translate.model.data.AppState
import com.example.translate.presenter.ITranslatePresenter

abstract class BaseTranslateActivity <T: AppState>: AppCompatActivity(), ITranslateView {

    protected val translatePresenter: ITranslatePresenter<ITranslateView, T> by lazy { createPresenter() }

    abstract fun createPresenter(): ITranslatePresenter<ITranslateView, T>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        translatePresenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        translatePresenter.detachView()
    }
}
package com.example.translate.view.base

import androidx.appcompat.app.AppCompatActivity
import com.example.translate.model.data.AppState
import com.example.translate.presenter.IMainPresenter

abstract class BaseMainActivity <T: AppState>: AppCompatActivity(), IMainView {

    protected val presenter: IMainPresenter<IMainView, T> by lazy { createPresenter() }

    abstract fun createPresenter(): IMainPresenter<IMainView, T>

    override fun onStart() {
        super.onStart()
        presenter.attachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detachView()
    }
}
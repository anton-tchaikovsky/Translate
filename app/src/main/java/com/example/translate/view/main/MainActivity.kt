package com.example.translate.view.main

import android.os.Bundle
import android.util.Log
import com.example.translate.databinding.ActivityMainBinding
import com.example.translate.model.data.AppState
import com.example.translate.presenter.IMainPresenter
import com.example.translate.presenter.MainPresenter
import com.example.translate.view.base.BaseMainActivity
import com.example.translate.view.base.IMainView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class MainActivity : BaseMainActivity<AppState>() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()
        presenter.onSearchWord("word")
    }
    override fun createPresenter(): IMainPresenter<IMainView, AppState> = MainPresenter(mainThreadScheduler = AndroidSchedulers.mainThread())

    override fun renderData(appState: AppState) {
        when (appState){
            is AppState.Success -> Log.d ("@@@", appState.dataModel.toString())
            is AppState.Error -> Log.d ("@@@", appState.error.toString())
            is AppState.Loading -> Log.d ("@@@", "loading")
        }
    }
}
package com.example.translate.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.translate.model.data.TranslateEntity
import com.example.translate.model.data.app_state.AppState
import com.example.translate.view.recycle_view.translate_recycle_view.TranslateAdapter
import com.example.translate.view_model.BaseTranslateViewModel

abstract class BaseTranslateActivity : AppCompatActivity() {

    protected abstract val translateViewModel: BaseTranslateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        translateViewModel.apply {
            getTranslateLiveData().observe(this@BaseTranslateActivity){
                renderData(it)
            }
            getSingleEventLiveData().observe(this@BaseTranslateActivity){
                renderData(it)
            }
        }
        initView()
    }

    protected val translateAdapter: TranslateAdapter by lazy {
        TranslateAdapter({openTranslatePhoto(it)}, {changeFavoritesState(it)})
    }

    abstract fun initView()

    abstract fun renderData(appState: AppState)

    private fun openTranslatePhoto(imageUrl: String) {
        startActivity(TranslateFotoActivity.getIntent(this, imageUrl))
    }

    private fun changeFavoritesState(translateEntity: TranslateEntity) {
        translateViewModel.onChangingFavoritesState(translateEntity)
    }

}
package com.example.translate.view

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.example.translate.view_model.TranslateFavoritesViewModel
import com.example.translate.view_model.view_model_factory.TranslateFavoritesViewModelFactory
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class TranslateFavoritesActivity:TranslateHistoryActivity() {

    private val translateFavoritesViewModelFactory: TranslateFavoritesViewModelFactory by inject {
        parametersOf(
            this
        )
    }

    override val translateViewModel: TranslateFavoritesViewModel by viewModels {
        translateFavoritesViewModelFactory
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, TranslateFavoritesActivity::class.java)
    }
}
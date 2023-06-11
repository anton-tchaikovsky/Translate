package com.example.translate.view

import android.content.Context
import android.content.Intent
import com.example.translate.view_model.translate_favorites_view_model.TranslateFavoritesViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TranslateFavoritesActivity:TranslateHistoryActivity() {

    override val translateViewModel: TranslateFavoritesViewModel by viewModel()

    companion object {
        fun getIntent(context: Context) = Intent(context, TranslateFavoritesActivity::class.java)
    }
}
package com.example.translate.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.core.databinding.ProgressLayoutBinding
import com.example.core.databinding.TranslateLayoutBinding
import com.example.core.view.BaseTranslateActivity
import com.example.translate.databinding.ActivityTranslateHistoryBinding
import com.example.translate.view_model.translate_history_view_model.TranslateHistoryViewModel
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.scope.Scope

open class TranslateHistoryActivity : BaseTranslateActivity(), AndroidScopeComponent {

    override val scope: Scope by activityScope()

    private lateinit var binding: ActivityTranslateHistoryBinding

    override lateinit var loadingBinding: ProgressLayoutBinding

    override lateinit var translateBinding: TranslateLayoutBinding

    override val translateViewModel: TranslateHistoryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTranslateHistoryBinding.inflate(layoutInflater)
        loadingBinding = binding.progressLayout
        translateBinding = binding.translateLayout
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
    }

    override fun initToolbar() {
        setSupportActionBar(binding.toolbar.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    override fun showInputWords(listInputWords: List<String>) {

    }

    companion object {
        fun getIntent(context: Context) = Intent(context, TranslateHistoryActivity::class.java)
    }

}
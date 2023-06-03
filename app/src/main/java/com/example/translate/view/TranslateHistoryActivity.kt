package com.example.translate.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.translate.R
import com.example.translate.databinding.ActivityTranslateHistoryBinding
import com.example.translate.model.data.TranslateEntity
import com.example.translate.model.data.app_state.AppState
import com.example.translate.view_model.translate_history_view_model.TranslateHistoryViewModel
import com.example.translate.view_model.view_model_factory.TranslateHistoryViewModelFactory
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

open class TranslateHistoryActivity : BaseTranslateActivity() {

    private lateinit var binding: ActivityTranslateHistoryBinding

    private val translateHistoryViewModelFactory: TranslateHistoryViewModelFactory by inject {
        parametersOf(
            this
        )
    }

    override val translateViewModel: TranslateHistoryViewModel by viewModels {
        translateHistoryViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTranslateHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        initToolbar()
        hideProgressLayout()
        initTranslateLayout()
        translateViewModel.onInitView()
    }

    override fun renderData(appState: AppState) {
        when (appState) {

            is AppState.EmptyData -> {
                showEmptyData()
                hideProgressLayout()
            }

            is AppState.Error -> {
                showError(appState.error)
                hideProgressLayout()
            }

            is AppState.Loading -> showProgressLayout()

            is AppState.Success -> {
                showTranslate(appState.listTranslateEntity)
                hideProgressLayout()
            }

            is AppState.Info -> showInfo(appState.info)

            is AppState.InputWords -> {

            }

            is AppState.SuccessChangeFavorites -> updateTranslateEntity(appState.updatePosition, appState.listTranslateEntity)
        }
    }

    private fun updateTranslateEntity(updatePosition: Int, listTranslateEntity: List<TranslateEntity>) {
        translateAdapter.updateListTranslateEntity(updatePosition, listTranslateEntity)
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun hideProgressLayout() {
        binding.progressLayout.root.visibility = View.GONE
        binding.translateLayout.root.visibility = View.VISIBLE
    }

    private fun initTranslateLayout() {
        binding.translateLayout.root.visibility = View.GONE
        initTranslateRecyclerView()
    }

    private fun showEmptyData() {
        translateAdapter.updateListTranslateEntity()
    }

    private fun showTranslate(listTranslateEntity: List<TranslateEntity>) {
        translateAdapter.updateListTranslateEntity(listTranslateEntity)
    }

    private fun showInfo(info: String) {
        Snackbar.make(binding.root, info, Snackbar.LENGTH_SHORT).show()
    }

    private fun showError(error: Throwable) {
        Toast.makeText(this, error.message.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun showProgressLayout() {
        binding.translateLayout.root.visibility = View.GONE
        binding.progressLayout.root.visibility = View.VISIBLE
    }

    private fun initTranslateRecyclerView() {
        binding.translateLayout.translateRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@TranslateHistoryActivity, RecyclerView.VERTICAL, false)
            adapter = translateAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@TranslateHistoryActivity,
                    LinearLayoutManager.VERTICAL
                ).also {
                    ContextCompat.getDrawable(context, R.drawable.divider_item_recycle_view)
                        ?.let { drawable ->
                            it.setDrawable(drawable)
                        }
                })
        }
    }

    companion object {
        fun getIntent(context: Context) = Intent(context, TranslateHistoryActivity::class.java)
    }

}
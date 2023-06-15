package com.example.core.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.R
import com.example.core.databinding.ProgressLayoutBinding
import com.example.core.databinding.TranslateLayoutBinding
import com.example.core.view.recycle_view.TranslateAdapter
import com.example.model.data.TranslateEntity
import com.example.model.data.app_state.AppState
import com.example.core.view_model.BaseTranslateViewModel
import com.example.translate_foto_screen.TranslateFotoActivity
import com.google.android.material.snackbar.Snackbar

abstract class BaseTranslateActivity : AppCompatActivity() {

    abstract var loadingBinding: ProgressLayoutBinding

   abstract var translateBinding: TranslateLayoutBinding

    protected abstract val translateViewModel: BaseTranslateViewModel

    private val translateAdapter: TranslateAdapter by lazy {
        TranslateAdapter({
            openTranslatePhoto(
                it
            )
        }, { changeFavoritesState(it) })
    }

    protected open fun initView() {
        initToolbar()
        hideProgressLayout()
        initTranslateLayout()
        translateViewModel.onInitView()
    }

    abstract fun initToolbar()

    abstract fun showInputWords(listInputWords: List<String>)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        translateViewModel.apply {
            getTranslateLiveData().observe(this@BaseTranslateActivity) {
                renderData(it)
            }
            getSingleEventLiveData().observe(this@BaseTranslateActivity) {
                renderData(it)
            }
        }
        initView()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                showTranslate(appState.listTranslateEntity)
                hideProgressLayout()
            }

            is AppState.Error -> {
                showError(appState.error)
                hideProgressLayout()
            }

            is AppState.Info -> {
                showInfo(appState.info)
                hideProgressLayout()
            }

            is AppState.Loading -> showProgressLayout()

            is AppState.EmptyData -> {
                showEmptyData()
                hideProgressLayout()
            }

            is AppState.InputWords -> {
                showInputWords(appState.listInputWords)
            }

            is AppState.SuccessChangeFavorites -> updateTranslateEntity(
                appState.updatePosition,
                appState.listTranslateEntity
            )
        }
    }

    private fun updateTranslateEntity(
        updatePosition: Int,
        listTranslateEntity: List<TranslateEntity>
    ) {
        translateAdapter.updateListTranslateEntity(updatePosition, listTranslateEntity)
    }

    private fun showEmptyData() {
        translateAdapter.updateListTranslateEntity()
    }

    private fun showTranslate(listTranslateEntity: List<TranslateEntity>) {
        translateAdapter.updateListTranslateEntity(listTranslateEntity)
    }

    private fun showInfo(info: String) {
        Snackbar.make(translateBinding.root, info, Snackbar.LENGTH_SHORT).show()
    }

    private fun showError(error: Throwable) {
        Toast.makeText(this, error.message.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun showProgressLayout() {
        translateBinding.root.visibility = View.INVISIBLE
        loadingBinding.root.visibility = View.VISIBLE
    }

    private fun hideProgressLayout() {
        loadingBinding.root.visibility = View.GONE
        translateBinding.root.visibility = View.VISIBLE
    }

    private fun openTranslatePhoto(imageUrl: String) {
        startActivity(TranslateFotoActivity.getIntent(this, imageUrl))
    }

    private fun changeFavoritesState(translateEntity: TranslateEntity) {
        translateViewModel.onChangingFavoritesState(translateEntity)
    }

    private fun initTranslateLayout() {
        translateBinding.root.visibility = View.INVISIBLE
        initTranslateRecyclerView()
    }

    private fun initTranslateRecyclerView() {
        translateBinding.translateRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@BaseTranslateActivity, RecyclerView.VERTICAL, false)
            adapter = translateAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@BaseTranslateActivity,
                    LinearLayoutManager.VERTICAL
                ).also {
                    ContextCompat.getDrawable(context,
                        R.drawable.divider_item_recycle_view
                    )
                        ?.let { drawable ->
                            it.setDrawable(drawable)
                        }
                })
        }
    }

}
package com.example.translate.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.translate.R
import com.example.translate.databinding.ActivityTranslateBinding
import com.example.translate.model.data.AppState
import com.example.translate.presenter.ITranslatePresenter
import com.example.translate.presenter.TranslatePresenter
import com.example.translate.view.translate_recycle_view.TranslateAdapter
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class TranslateActivity : BaseTranslateActivity<AppState>() {

    private lateinit var binding: ActivityTranslateBinding

    private val translateAdapter: TranslateAdapter by lazy {
        TranslateAdapter(itemTranslatePresenter = this@TranslateActivity.translatePresenter.itemTranslatePresenter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        hideKeybooard()
        binding = ActivityTranslateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun onRetainCustomNonConfigurationInstance(): ITranslatePresenter<ITranslateView, AppState> {
        return translatePresenter
    }

    override fun initView() {
        initSearchTextLayout()
        hideProgressLayout()
        initTranslateRecycleView()
    }

    @Suppress("UNCHECKED_CAST", "DEPRECATION")
    override fun createPresenter(): ITranslatePresenter<ITranslateView, AppState> =
        lastCustomNonConfigurationInstance as? TranslatePresenter<ITranslateView, AppState>
            ?: TranslatePresenter(mainThreadScheduler = AndroidSchedulers.mainThread())

    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                showTranslate()
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
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showTranslate() {
        translateAdapter.notifyDataSetChanged()
        binding.translateLayout.root.visibility = View.VISIBLE
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showInfo(info: String) {
        translateAdapter.notifyDataSetChanged()
        binding.translateLayout.root.visibility = View.VISIBLE
        Snackbar.make(binding.root, info, Snackbar.LENGTH_SHORT).show()
    }

    private fun showError(error: Throwable) {
        Toast.makeText(this, error.message.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun showProgressLayout() {
        binding.translateLayout.root.visibility = View.GONE
        binding.progressLayout.root.visibility = View.VISIBLE
    }

    private fun hideProgressLayout() {
        binding.progressLayout.root.visibility = View.GONE
    }

    private fun initSearchTextLayout() {
        initClearSearchTextImageView()
        initSearchTextEditText()
        initSearchTextFab()
    }

    private fun initSearchTextFab() {
        binding.searchTextLayout.searchTextFab.setOnClickListener {
            hideKeybooard()
            translatePresenter.onSearchWord(binding.searchTextLayout.searchTextEditText.text.toString())
        }
    }

    private fun initSearchTextEditText() {
        binding.searchTextLayout.run {
            searchTextEditText.doOnTextChanged { text, _, _, _ ->
                if (text.isNullOrEmpty())
                    clearSearchTextImageView.visibility = View.GONE
                else
                    clearSearchTextImageView.visibility = View.VISIBLE
            }
        }
    }

    private fun initClearSearchTextImageView() {
        binding.searchTextLayout.clearSearchTextImageView.run {
            visibility = View.GONE
            setOnClickListener {
                binding.searchTextLayout.searchTextEditText.text?.clear()
                it.visibility = View.GONE
            }
        }
    }

    private fun initTranslateRecycleView() {
        binding.translateLayout.run {
            root.visibility = View.GONE
            translateRecyclerView.apply {
                layoutManager =
                    LinearLayoutManager(this@TranslateActivity, RecyclerView.VERTICAL, false)
                adapter = translateAdapter
                addItemDecoration(
                    DividerItemDecoration(
                        this@TranslateActivity,
                        LinearLayoutManager.VERTICAL
                    ).also {
                        ContextCompat.getDrawable(context, R.drawable.divider_item_recycle_view)
                            ?.let { drawable ->
                                it.setDrawable(drawable)
                            }
                    })
            }
        }
    }

    private fun hideKeybooard(){
        currentFocus?.let{
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

}
package com.example.translate.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.translate.databinding.ActivityTranslateBinding
import com.example.translate.model.data.AppState
import com.example.translate.presenter.ITranslatePresenter
import com.example.translate.presenter.TranslatePresenter
import com.example.translate.view.translate_recycle_view.TranslateAdapter
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class TranslateActivity : BaseTranslateActivity<AppState>() {

    private lateinit var binding: ActivityTranslateBinding

    private val translateAdapter: TranslateAdapter by lazy {
        TranslateAdapter(itemTranslatePresenter = this@TranslateActivity.translatePresenter.itemTranslatePresenter)
    }

    private var searchDialogFragment: SearchDialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTranslateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        initTranslateLayout()
        hideProgressLayout()
        initTranslateRecycleView()
    }

    override fun showSearchDialog() {
        searchDialogFragment = SearchDialogFragment.newInstance()
        searchDialogFragment?.run {
            setOnSearchWordClickListener {
                translatePresenter.onSearchWord(it)
            }
            show(supportFragmentManager, SearchDialogFragment.TAG_SEARCH_DIALOG_FRAGMENT)
        }
    }

    override fun hideSearchDialog() {
        searchDialogFragment?.dismiss()
    }

    override fun createPresenter(): ITranslatePresenter<ITranslateView, AppState> =
        TranslatePresenter(mainThreadScheduler = AndroidSchedulers.mainThread())


    override fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> showTranslate()
            is AppState.Error -> showError (appState.error)
            is AppState.Loading -> showProgressLayout()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showTranslate() {
        translateAdapter.notifyDataSetChanged()
    }

    private fun showError(error: Throwable) {
        Toast.makeText(this, error.message.toString(), Toast.LENGTH_SHORT).show()
    }

    private fun showProgressLayout() {
        binding.progressLayout.root.visibility = View.VISIBLE
    }

    private fun hideProgressLayout() {
        binding.progressLayout.root.visibility = View.GONE
    }

    private fun initTranslateLayout() {
        binding.translateLayout.root.visibility = View.VISIBLE
        initSearchFab()
    }

    private fun initSearchFab() {
        binding.translateLayout.searchFab.setOnClickListener {
            translatePresenter.onSearchDialog()
        }
    }

    private fun initTranslateRecycleView() {
        binding.translateLayout.translateRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@TranslateActivity, RecyclerView.VERTICAL, false)
            adapter = translateAdapter
        }
    }

}
package com.example.translate.view

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.databinding.ProgressLayoutBinding
import com.example.core.databinding.TranslateLayoutBinding
import com.example.core.view.BaseTranslateActivity
import com.example.translate.R
import com.example.translate.databinding.ActivityTranslateBinding
import com.example.translate.view.recycle_view.input_words_recycle_view.InputWordAdapter
import com.example.translate.view_model.translate_view_model.TranslateViewModel
import com.example.translate.view_model.view_model_factory.TranslateSavedStateViewModelFactory
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class TranslateActivity : BaseTranslateActivity() {

    private lateinit var binding: ActivityTranslateBinding

    override lateinit var loadingBinding: ProgressLayoutBinding

    override lateinit var translateBinding: TranslateLayoutBinding

    private val inputWordsAdapter: InputWordAdapter by lazy {
        InputWordAdapter {
            selectingInputWord(it)
        }
    }

    private var isSelectedInputWord = false

    private val translateSavedStateViewModelFactory: TranslateSavedStateViewModelFactory by inject {
        parametersOf(
            this
        )
    }

    override val translateViewModel: TranslateViewModel by viewModels {
        translateSavedStateViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityTranslateBinding.inflate(layoutInflater)
        loadingBinding = binding.progressLayout
        translateBinding = binding.translateLayout
        setContentView(binding.root)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_translate_activity, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.history -> {
                openTranslateHistory()
                return true
            }

            R.id.favorites -> {
                openTranslateFavorites()
                return true
            }
        }
        return false
    }

    override fun initView() {
        super.initView()
        initSearchTextLayout()
    }

    override fun showInputWords(listInputWords: List<String>) {
        inputWordsAdapter.updateListInputWord(listInputWords)
    }


    override fun initToolbar() {
        binding.toolbar.toolbar.apply {
            navigationIcon = null
            setSupportActionBar(this@apply)
        }
    }

    private fun initSearchTextLayout() {
        initClearSearchTextImageView()
        initSearchTextEditText()
        initSearchTextFab()
        initInputWordRecyclerView()
    }

    private fun initSearchTextFab() {
        binding.searchTextLayout.searchTextFab.setOnClickListener {
            hideKeyboard()
            inputWordsAdapter.updateListInputWord(listOf())
            translateViewModel.onSearchWord(binding.searchTextLayout.searchTextEditText.text.toString())
        }
    }

    private fun initSearchTextEditText() {
        binding.searchTextLayout.run {
            searchTextEditText.doOnTextChanged { text, _, _, _ ->
                if (!isSelectedInputWord)
                    translateViewModel.onChangingInputWord(text.toString())
                isSelectedInputWord = false
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
                translateViewModel.onChangingInputWord("")
            }
        }
    }

    private fun initInputWordRecyclerView() {
        binding.searchTextLayout.inputWordsRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@TranslateActivity, RecyclerView.VERTICAL, false)
            adapter = inputWordsAdapter
        }
    }

    private fun hideKeyboard() {
        currentFocus?.let {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }

    private fun selectingInputWord(inputWord: String) {
        isSelectedInputWord = true
        binding.searchTextLayout.searchTextEditText.setText(inputWord)
        inputWordsAdapter.updateListInputWord(listOf())
    }

    private fun openTranslateFavorites() {
        startActivity(TranslateFavoritesActivity.getIntent(this))
    }

    private fun openTranslateHistory() {
        startActivity(TranslateHistoryActivity.getIntent(this))
    }

}

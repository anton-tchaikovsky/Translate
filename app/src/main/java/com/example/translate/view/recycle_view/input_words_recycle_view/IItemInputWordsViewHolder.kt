package com.example.translate.view.recycle_view.input_words_recycle_view

import com.example.translate.view.recycle_view.IItemRecycleView

interface IItemInputWordsViewHolder: IItemRecycleView<String> {

    val inputWordTextViewClickListener: (String) -> Unit

    override fun bind(entity: String) {
        entity.apply {
            showInputWord(entity)
            setInputWordTextViewClickListener(inputWordTextViewClickListener)
        }
    }

    fun showInputWord(inputWord: String)

    fun setInputWordTextViewClickListener(inputWordTextViewClickListener: (String) -> Unit)

}
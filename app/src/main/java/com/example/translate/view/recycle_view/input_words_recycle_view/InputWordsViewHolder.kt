package com.example.translate.view.recycle_view.input_words_recycle_view

import androidx.recyclerview.widget.RecyclerView
import com.example.translate.databinding.ItemInputWordLayoutBinding

class InputWordsViewHolder(
    private val binding: ItemInputWordLayoutBinding,
    override val inputWordTextViewClickListener: (String) -> Unit
) :
    RecyclerView.ViewHolder(binding.root),
    IItemInputWordsViewHolder {

    override fun showInputWord(inputWord: String) {
        binding.inputWordTextView.apply {
            text = inputWord

        }
    }

    override fun setInputWordTextViewClickListener(inputWordTextViewClickListener: (String) -> Unit) {
        binding.inputWordTextView.apply {
            setOnClickListener {
                inputWordTextViewClickListener(text.toString())
            }
        }
    }

}
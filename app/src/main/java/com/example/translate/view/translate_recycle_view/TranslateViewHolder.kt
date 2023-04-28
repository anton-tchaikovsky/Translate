package com.example.translate.view.translate_recycle_view

import androidx.recyclerview.widget.RecyclerView
import com.example.translate.databinding.ItemTranslateCardViewBinding

class TranslateViewHolder (private val binding: ItemTranslateCardViewBinding):RecyclerView.ViewHolder(binding.root),
    IItemTranslateViewHolder {

    override var itemPosition: Int? = null

    override fun showTranslate(translate: String) {
        binding.translateTextView.text = translate
    }

    override fun showNote(note: String) {
        binding.noteTextView.text = note
    }

}
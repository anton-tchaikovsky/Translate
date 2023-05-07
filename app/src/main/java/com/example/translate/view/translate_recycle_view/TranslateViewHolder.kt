package com.example.translate.view.translate_recycle_view

import androidx.recyclerview.widget.RecyclerView
import com.example.translate.databinding.ItemTranslateLayoutBinding

class TranslateViewHolder (private val binding: ItemTranslateLayoutBinding):RecyclerView.ViewHolder(binding.root),
    IItemTranslateViewHolder {

    override fun showTextTranslate(textTranslate: String) {
        binding.textTranslateTextView.text = textTranslate
    }

    override fun showText(text: String) {
        binding.textTextView.text = text
    }

    override fun showTranscription(transcription: String) {
        binding.transcriptionTextView.text = transcription
    }

}
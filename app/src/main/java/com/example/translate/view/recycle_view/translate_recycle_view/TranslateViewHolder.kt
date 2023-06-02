package com.example.translate.view.recycle_view.translate_recycle_view

import androidx.recyclerview.widget.RecyclerView
import com.example.translate.databinding.ItemTranslateLayoutBinding

class TranslateViewHolder (private val binding: ItemTranslateLayoutBinding,
                           override val openFotoImageViewClickListener: (urlImage: String) -> Unit
):RecyclerView.ViewHolder(binding.root),
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

    override fun setOpenFotoImageViewClickListener(
        imageUrl: String,
        openFotoImageViewClickListener: (imageUrl: String) -> Unit
    ) {
        binding.openFotoImageView.setOnClickListener {
            openFotoImageViewClickListener(imageUrl)
        }
    }

}
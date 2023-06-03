package com.example.translate.view.recycle_view.translate_recycle_view

import androidx.recyclerview.widget.RecyclerView
import com.example.translate.R
import com.example.translate.databinding.ItemTranslateLayoutBinding
import com.example.translate.model.data.TranslateEntity

class TranslateViewHolder(
    private val binding: ItemTranslateLayoutBinding,
    override val openFotoImageViewClickListener: (urlImage: String) -> Unit,
    override val favoritesImageViewClickListener: (translateEntity: TranslateEntity) -> Unit
) : RecyclerView.ViewHolder(binding.root),
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

    override fun setFavoritesImage(isFavorites: Boolean) {
        binding.favoritesImageView.apply {
            if (isFavorites)
                setImageResource(R.drawable.baseline_favorite_24)
            else
                setImageResource(R.drawable.baseline_favorite_border_24)
        }
    }

    override fun setFavoritesImageViewClickListener(
        translateEntity: TranslateEntity,
        favoritesImageViewClickListener: (translateEntity: TranslateEntity) -> Unit
    ) {
        binding.favoritesImageView.setOnClickListener {
            favoritesImageViewClickListener(translateEntity)
        }
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
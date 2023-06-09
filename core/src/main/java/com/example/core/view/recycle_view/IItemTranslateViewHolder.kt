package com.example.core.view.recycle_view

import com.example.model.data.TranslateEntity

interface IItemTranslateViewHolder: IItemRecycleView<TranslateEntity> {

    val openFotoImageViewClickListener: (imageUrl:String)->Unit
    val favoritesImageViewClickListener: (translateEntity: TranslateEntity) -> Unit

    override fun bind(entity: TranslateEntity) {
        entity.apply {
            showText(text)
            showTranscription (transcription)
            showTextTranslate(textTranslation)
            setFavoritesImage(isFavorites)
            setFavoritesImageViewClickListener(this, favoritesImageViewClickListener)
            setOpenFotoImageViewClickListener(imageUrl, openFotoImageViewClickListener)
        }
    }

    fun showTextTranslate(textTranslate: String)

    fun showText(text: String)

    fun showTranscription (transcription: String)

    fun setFavoritesImage(isFavorites: Boolean)

    fun setFavoritesImageViewClickListener (translateEntity: TranslateEntity, favoritesImageViewClickListener: (translateEntity: TranslateEntity) -> Unit)

    fun setOpenFotoImageViewClickListener(imageUrl: String, openFotoImageViewClickListener: (imageUrl:String)->Unit)

}
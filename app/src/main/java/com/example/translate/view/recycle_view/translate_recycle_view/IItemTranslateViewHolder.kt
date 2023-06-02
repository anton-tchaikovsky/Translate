package com.example.translate.view.recycle_view.translate_recycle_view

import com.example.translate.model.data.TranslateEntity
import com.example.translate.view.recycle_view.IItemRecycleView

interface IItemTranslateViewHolder: IItemRecycleView<TranslateEntity> {

    val openFotoImageViewClickListener: (imageUrl:String)->Unit

    override fun bind(entity: TranslateEntity) {
        entity.apply {
            showText(text)
            showTranscription (transcription)
            showTextTranslate(textTranslation)
            setOpenFotoImageViewClickListener(imageUrl, openFotoImageViewClickListener)
        }
    }

    fun showTextTranslate(textTranslate: String)

    fun showText(text: String)

    fun showTranscription (transcription: String)

    fun setOpenFotoImageViewClickListener(imageUrl: String, openFotoImageViewClickListener: (imageUrl:String)->Unit)

}
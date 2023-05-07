package com.example.translate.view.translate_recycle_view

import com.example.translate.model.data.TranslateEntity

interface IItemTranslateViewHolder: IItemRecycleView<TranslateEntity> {

    override fun bind(entity: TranslateEntity) {
        entity.apply {
            showText(text)
            showTranscription (transcription)
            showTextTranslate(textTranslation)
        }

    }

    fun showTextTranslate(textTranslate: String)

    fun showText(text: String)

    fun showTranscription (transcription: String)

}
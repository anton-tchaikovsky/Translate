package com.example.translate.presenter.translate_recycle_view

import com.example.translate.model.data.dto.Meaning
import com.example.translate.view.translate_recycle_view.IItemTranslateViewHolder

class ItemTranslatePresenter(override val entityList: MutableList<Meaning> = mutableListOf()) :
    IItemTranslatePresenter {

    override fun getCount() = entityList.size

    override fun getId(itemPosition: Int) = entityList[itemPosition].id

    override fun bindView(itemView: IItemTranslateViewHolder) {
        itemView.itemPosition?.let {
            with(entityList[it]) {
                itemView.showTranslate(translation.text)
                translation.note?.let { note ->
                    itemView.showNote(note)
                }
            }
        }
    }

}
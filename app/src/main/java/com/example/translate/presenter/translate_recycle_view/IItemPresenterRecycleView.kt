package com.example.translate.presenter.translate_recycle_view

import com.example.translate.view.translate_recycle_view.IItemRecycleView

interface IItemPresenterRecycleView <itemRecycleView: IItemRecycleView, entity: Any> {
    val entityList:MutableList<entity>
    fun getCount():Int
    fun getId(itemPosition:Int):Int
    fun bindView(itemView: itemRecycleView)
}
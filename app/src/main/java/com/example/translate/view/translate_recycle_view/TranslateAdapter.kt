package com.example.translate.view.translate_recycle_view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.translate.databinding.ItemTranslateCardViewBinding
import com.example.translate.presenter.translate_recycle_view.IItemTranslatePresenter

class TranslateAdapter (private val itemTranslatePresenter: IItemTranslatePresenter):RecyclerView.Adapter<TranslateViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TranslateViewHolder {
        val binding = ItemTranslateCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TranslateViewHolder(binding)
    }

    override fun getItemCount(): Int = itemTranslatePresenter.getCount()

    override fun onBindViewHolder(holder: TranslateViewHolder, position: Int) {
        holder.itemPosition = position
        itemTranslatePresenter.bindView(holder)
    }

    override fun getItemId(position: Int) = itemTranslatePresenter.getId(position).toLong()

}
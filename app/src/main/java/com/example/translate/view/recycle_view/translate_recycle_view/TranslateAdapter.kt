package com.example.translate.view.recycle_view.translate_recycle_view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.translate.databinding.ItemTranslateLayoutBinding
import com.example.translate.model.data.TranslateEntity

class TranslateAdapter: RecyclerView.Adapter<TranslateViewHolder>() {

    private var listTranslateEntity: List<TranslateEntity> = listOf()

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TranslateViewHolder {
        val binding = ItemTranslateLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TranslateViewHolder(binding)
    }

    override fun getItemCount(): Int = listTranslateEntity.size

    override fun onBindViewHolder(holder: TranslateViewHolder, position: Int) {
        holder.bind(listTranslateEntity[position])
    }

    override fun getItemId(position: Int) = listTranslateEntity[position].id.toLong()

    @SuppressLint("NotifyDataSetChanged")
    fun updateListTranslateEntity(listTranslateEntity: List<TranslateEntity> = listOf()){
        this.listTranslateEntity = listTranslateEntity
        notifyDataSetChanged()
    }

}
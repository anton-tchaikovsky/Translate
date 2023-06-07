package com.example.core.view.recycle_view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.core.databinding.ItemTranslateLayoutBinding

class TranslateAdapter(private val openFotoImageViewClickListener: (urlImage: String) -> Unit,
private val favoritesImageViewClickListener: (translateEntity: com.example.model.data.TranslateEntity)->Unit) :
    RecyclerView.Adapter<TranslateViewHolder>(
    ) {

    private var listTranslateEntity: List<com.example.model.data.TranslateEntity> = listOf()

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TranslateViewHolder {
        val binding =
            ItemTranslateLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TranslateViewHolder(binding, openFotoImageViewClickListener, favoritesImageViewClickListener)
    }

    override fun getItemCount(): Int = listTranslateEntity.size

    override fun onBindViewHolder(holder: TranslateViewHolder, position: Int) {
        holder.bind(listTranslateEntity[position])
    }

    override fun getItemId(position: Int) = listTranslateEntity[position].id.toLong()

    @SuppressLint("NotifyDataSetChanged")
    fun updateListTranslateEntity(listTranslateEntity: List<com.example.model.data.TranslateEntity> = listOf()) {
        this.listTranslateEntity = listTranslateEntity
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateListTranslateEntity(updatePosition: Int, listTranslateEntity: List<com.example.model.data.TranslateEntity> = listOf()) {
        this.listTranslateEntity = listTranslateEntity
        notifyItemChanged(updatePosition)
    }

}
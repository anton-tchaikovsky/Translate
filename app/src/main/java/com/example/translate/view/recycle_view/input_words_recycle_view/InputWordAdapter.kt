package com.example.translate.view.recycle_view.input_words_recycle_view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.translate.databinding.ItemInputWordLayoutBinding

class InputWordAdapter(private val inputWordTextViewClickListener: (String) -> Unit) :
    RecyclerView.Adapter<InputWordsViewHolder>() {

    private var listInputWord: List<String> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InputWordsViewHolder {
        val binding =
            ItemInputWordLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return InputWordsViewHolder(binding, inputWordTextViewClickListener)
    }

    override fun getItemCount(): Int = listInputWord.size

    override fun onBindViewHolder(holder: InputWordsViewHolder, position: Int) {
        holder.bind(listInputWord[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateListInputWord(listInputWord: List<String> = listOf()) {
        this.listInputWord = listInputWord
        notifyDataSetChanged()
    }

}
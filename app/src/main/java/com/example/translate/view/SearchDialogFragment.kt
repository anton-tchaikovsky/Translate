package com.example.translate.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.translate.databinding.SearchDialogLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SearchDialogFragment: BottomSheetDialogFragment() {

    private var _binding: SearchDialogLayoutBinding? = null
    private val binding: SearchDialogLayoutBinding
        get() = _binding!!

    private var onSearchWordClickListener: OnSearchWordClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchDialogLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchTextField.setStartIconOnClickListener {
            onSearchWordClickListener?.onClick(binding.searchEditText.text.toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun setOnSearchWordClickListener(onSearchWordClickListener: OnSearchWordClickListener){
        this.onSearchWordClickListener = onSearchWordClickListener
    }

    fun interface OnSearchWordClickListener {
        fun onClick (text: String?)
    }

    companion object{
        fun newInstance() = SearchDialogFragment()
        const val TAG_SEARCH_DIALOG_FRAGMENT = "TagSearchDialogFragment"
    }

}
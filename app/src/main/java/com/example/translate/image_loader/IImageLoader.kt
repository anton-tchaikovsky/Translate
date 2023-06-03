package com.example.translate.image_loader

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView

interface IImageLoader {

    val context: Context

    fun loadImage(
        imageUrl: String,
        imageView: AppCompatImageView,
        onStart: () -> Unit,
        onSuccess: (image: Drawable?) -> Unit,
        onError: () -> Unit
    )

}
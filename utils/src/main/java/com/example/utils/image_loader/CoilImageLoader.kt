package com.example.utils.image_loader

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import coil.ImageLoader
import coil.request.ImageRequest
import coil.size.Scale
import coil.transform.RoundedCornersTransformation

class CoilImageLoader(override val context: Context) : IImageLoader {

    override fun loadImage(
        imageUrl: String,
        imageView: AppCompatImageView,
        onStart: () -> Unit,
        onSuccess: (image: Drawable?) -> Unit,
        onError: () -> Unit
    ) {
        ImageRequest.Builder(context)
            .data(imageUrl)
            .transformations(RoundedCornersTransformation(ROUNDED_CORNERS))
            .scale(Scale.FIT)
            .target(
                onStart = {
                    onStart()
                },
                onSuccess = {
                    onSuccess(it)
                },
                onError = {
                   onError()
                }
            )
            .build().also {
                ImageLoader.Builder(context).build().enqueue(it)
            }
    }

    companion object{
        private const val ROUNDED_CORNERS = 100f
    }

}
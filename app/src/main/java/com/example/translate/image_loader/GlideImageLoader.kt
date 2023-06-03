package com.example.translate.image_loader

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class GlideImageLoader(override val context: Context) : IImageLoader {

    override fun loadImage(
        imageUrl: String,
        imageView: AppCompatImageView,
        onStart: () -> Unit,
        onSuccess: (image: Drawable?) -> Unit,
        onError: () -> Unit
    ) {
        onStart()
        Glide.with(context)
            .load(imageUrl)
            .fitCenter()
            .transform(RoundedCorners(ROUNDED_CORNERS))
            .addListener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    onError()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    onSuccess(resource)
                    return true
                }
            })
            .into(imageView)

    }

    companion object {
        private const val ROUNDED_CORNERS = 100
    }

}
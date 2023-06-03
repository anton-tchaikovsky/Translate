package com.example.translate.image_loader

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.widget.AppCompatImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

class PicassoImageLoader(override val context: Context) : IImageLoader {

    override fun loadImage(
        imageUrl: String,
        imageView: AppCompatImageView,
        onStart: () -> Unit,
        onSuccess: (image: Drawable?) -> Unit,
        onError: () -> Unit
    ) {
        onStart()
        Picasso.get()
            .load(imageUrl)
            .fit()
            .centerInside()
            .transform(RoundedCornersTransformation(ROUNDED_CORNERS, 0))
            .into(imageView, object: Callback{
                override fun onSuccess() {
                    onSuccess(null)
                }
                override fun onError(e: Exception?) {
                    onError()
                }

            })
    }

    companion object{
        private const val ROUNDED_CORNERS = 100
    }

}
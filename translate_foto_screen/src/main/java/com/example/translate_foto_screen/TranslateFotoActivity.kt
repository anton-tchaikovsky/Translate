package com.example.translate_foto_screen

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.utils.image_loader.IImageLoader
import com.example.utils.viewById
import com.google.android.material.appbar.MaterialToolbar
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.core.scope.Scope

class TranslateFotoActivity : AppCompatActivity(), AndroidScopeComponent {

    override val scope: Scope by activityScope()

    private val imageLoader: IImageLoader by inject()

    private var imageUrl: String? = null

    private val toolbar by viewById<MaterialToolbar>(R.id.toolbar)

    private val translateFotoSwipeRefreshLayout by viewById<SwipeRefreshLayout>(R.id.translate_foto_swipe_refresh_layout)

    private val translateFotoImageView by viewById<AppCompatImageView>(R.id.translate_foto_image_view)

    private val placeholderImageView by viewById<AppCompatImageView>(R.id.placeholder_image_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translate_foto)
        initView()
        intent.extras?.let {
            imageUrl = it.getString(KEY_IMAGE_URL)?.also { imageUrl ->
                loadImage(imageUrl)
            }
        }
    }

    private fun initView() {
        initToolbar()
        initSwipeRefreshLayout()
    }

    private fun initToolbar() {
        toolbar.apply {
            setSupportActionBar(this@apply)
            setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun initSwipeRefreshLayout() {
        translateFotoSwipeRefreshLayout.apply {
            setColorSchemeResources(R.color.primary_theme_orange)
            setProgressBackgroundColorSchemeResource(R.color.surface_theme_orange)
            setOnRefreshListener {
                imageUrl?.let {
                    loadImage(it)
                }
            }
        }
    }

    private fun loadImage(imageUrl: String) {
        imageLoader.loadImage(
            imageUrl,
            translateFotoImageView,
            { showLoadingImage() },
            { showImage(it) },
            { showError() })
    }

    private fun showLoadingImage() {
        translateFotoSwipeRefreshLayout.apply {
            setBackgroundColor(resources.getColor(android.R.color.white, null))
            isRefreshing = true
        }
        translateFotoImageView.setImageDrawable(null)
        placeholderImageView.setImageResource(R.drawable.baseline_image_24)
    }

    private fun showImage(image: Drawable?) {
        translateFotoSwipeRefreshLayout.apply {
            setBackgroundColor(resources.getColor(android.R.color.black, null))
            isRefreshing = false
        }
        image?.let {
            translateFotoImageView.setImageDrawable(image)
        }
    }

    private fun showError() {
        translateFotoSwipeRefreshLayout.apply {
            setBackgroundColor(resources.getColor(android.R.color.white, null))
            isRefreshing = false
        }
        translateFotoImageView.setImageDrawable(null)
        placeholderImageView.setImageResource(R.drawable.baseline_image_not_supported_24)
        Toast.makeText(this, IMAGE_LOADING_ERROR, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun getIntent(context: Context, imageUrl: String) =
            Intent(context, TranslateFotoActivity::class.java).apply {
                putExtra(KEY_IMAGE_URL, imageUrl)
            }

        private const val KEY_IMAGE_URL = "KeyImageUrl"

        private const val IMAGE_LOADING_ERROR = "Не удалось загрузить изображение"
    }

}
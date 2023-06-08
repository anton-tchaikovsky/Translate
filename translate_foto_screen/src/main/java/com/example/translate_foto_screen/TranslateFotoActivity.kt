package com.example.translate_foto_screen

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.translate_foto_screen.databinding.ActivityTranslateFotoBinding
import com.example.utils.image_loader.IImageLoader
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.core.scope.Scope

class TranslateFotoActivity : AppCompatActivity(), AndroidScopeComponent {

    override val scope: Scope by activityScope()

    private lateinit var binding: ActivityTranslateFotoBinding

    private val imageLoader: IImageLoader by inject()

    private var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTranslateFotoBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
        binding.toolbar.apply {
            setSupportActionBar(this@apply)
            setNavigationOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun initSwipeRefreshLayout() {
        binding.translateFotoSwipeRefreshLayout.apply {
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
        imageLoader.loadImage(imageUrl, binding.translateFotoImageView, { showLoadingImage() }, {showImage(it)}, {showError()})
    }

    private fun showLoadingImage() {
        binding.translateFotoSwipeRefreshLayout.apply {
            setBackgroundColor(resources.getColor(android.R.color.white,null))
            isRefreshing = true
        }
        binding.translateFotoImageView.setImageDrawable(null)
        binding.placeholderImageView.setImageResource(R.drawable.baseline_image_24)
    }

    private fun showImage(image: Drawable?){
        binding.translateFotoSwipeRefreshLayout.apply {
            setBackgroundColor(resources.getColor(android.R.color.black,null))
            isRefreshing = false
        }
        image?.let {
            binding.translateFotoImageView.setImageDrawable(image)
        }
    }

    private fun showError(){
        binding.translateFotoSwipeRefreshLayout.apply {
            setBackgroundColor(resources.getColor(android.R.color.white,null))
            isRefreshing = false
        }
        binding.translateFotoImageView.setImageDrawable(null)
        binding.placeholderImageView.setImageResource(R.drawable.baseline_image_not_supported_24)
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
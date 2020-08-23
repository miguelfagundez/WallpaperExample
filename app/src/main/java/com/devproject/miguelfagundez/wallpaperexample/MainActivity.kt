package com.devproject.miguelfagundez.wallpaperexample

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.graphics.drawable.toBitmap
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    // Members
    private var imageView : ImageView? = null
    private var btnLoad : Button? = null
    private var btnSetWallpaper : Button? = null

    //Temp variables
    private var imageToDownload : String? = null
    private var progressBar : ProgressBar? = null

    // Url
    private var url : String = "https://picsum.photos/2000"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        imageView = findViewById(R.id.ivImage)
        btnLoad = findViewById(R.id.btnDownloadImage)
        btnSetWallpaper = findViewById(R.id.btnSetWallpaper)
        progressBar = findViewById(R.id.progressBar)
    }

    private fun setupListeners() {
        btnLoad?.setOnClickListener { view ->

            progressBar?.visibility = View.VISIBLE

            // we use the url https://picsum.photos/2000 to retrieve an image
            imageToDownload = url
            Toast.makeText(this,"Using URL by default: "  + imageToDownload,Toast.LENGTH_SHORT).show()

            // We try to retrieve the image into the ImageView
            Glide.with(this)
                .load(imageToDownload)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(imageView)
        }

        // Setting the BItmap into the Wallpaper
        btnSetWallpaper?.setOnClickListener { view ->

            if (imageView?.drawable == null){
                Toast.makeText(this,"Wallpaper null!",Toast.LENGTH_SHORT).show()
            }else{
                // Taking Wallpaper Manager
                var wallpaperManager : WallpaperManager = WallpaperManager.getInstance(applicationContext)
                var drawable : Drawable? = imageView?.drawable as Drawable

                // Taking Bitmap image from any resources (web, drawable, etc)
                wallpaperManager?.setBitmap(drawable?.toBitmap())
                Toast.makeText(this,"Wallpaper set!",Toast.LENGTH_SHORT).show()

            }

        }
    }

    override fun onResume() {
        super.onResume()
        imageView?.setImageDrawable(null)
        progressBar?.visibility = View.INVISIBLE
    }
}
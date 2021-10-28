package com.example.pexels.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pexels.R
import com.example.pexels.databinding.ActivityImageDetailBinding
import java.util.*
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.*
import java.net.URL

import android.os.AsyncTask
import coil.load
import java.lang.Exception


class ImageDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageDetailBinding

    companion object {
        const val IMAGE = "image"
        const val AUTHOR = "author"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_detail)

        binding = ActivityImageDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val img = intent.getStringExtra(IMAGE)
        val author = intent.getStringExtra(AUTHOR)

        binding.imgDetail.load(img) {
            crossfade(true)
            crossfade(1000)
            placeholder(android.R.color.darker_gray)
            error(R.drawable.nodata)
        }

        binding.txtAuthor.text = getString(R.string.author, author)

        binding.btnSave.setOnClickListener {
//            val uri: String = Uri.parse(IMAGE).toString().trim()
            val uri : Uri = Uri.parse(IMAGE)
//            DownloadImageFromPath(uri.toString())

        }
    }

}
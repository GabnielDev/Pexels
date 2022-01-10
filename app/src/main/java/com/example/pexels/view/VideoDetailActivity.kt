package com.example.pexels.view

import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import com.example.pexels.R
import com.example.pexels.databinding.ActivityVideoDetailBinding
import android.widget.Toast


class VideoDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoDetailBinding

    companion object {
        const val VIDEO = "video"
        const val AUTHOR = "author"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_detail)

        binding = ActivityVideoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val video = intent.getStringExtra(VIDEO)
        val author = intent.getStringExtra(AUTHOR)

        val uri: Uri = Uri.parse(video)
        Log.e("uriData", "$uri")
        val mediaController = MediaController(this)

        binding.txtAuthor.text = getString(R.string.author, author)

        if (mediaController == null) {
            val mediaController = MediaController(applicationContext)
            mediaController.setAnchorView(binding.videoDetail)
        }

        binding.videoDetail.setMediaController(mediaController)
        binding.videoDetail.setVideoURI(uri)
        binding.videoDetail.start()

        binding.videoDetail.setOnCompletionListener(OnCompletionListener {
            Toast.makeText(applicationContext, "Thank You...!!!", Toast.LENGTH_LONG)
                .show() // display a toast when an video is completed
        })

        binding.videoDetail.setOnErrorListener(MediaPlayer.OnErrorListener { mp, what, extra ->
            Toast.makeText(
                applicationContext,
                "Oops An Error Occur While Playing Video...!!!",
                Toast.LENGTH_LONG
            ).show() // display a toast when an error is occured while playing an video
            false
        })

    }
}
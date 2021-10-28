package com.example.pexels.utils

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.widget.Toast

object Constants {

    const val BASEURL = "https://api.pexels.com/"
    const val IMAGE = "v1/curated"
    const val VIDEO = "videos/popular"
    const val SEARCHIMAGE = "v1/search"
    const val SEARCHVIDEO = "videos/search"
    const val APIKEY = "Authorization: 563492ad6f91700001000001da4f04d4ff4142e7868ece13304ecea1"

    fun View.visible(isVisible: Boolean) {
        visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    fun View.enable(isEnabled: Boolean) {
        setEnabled(isEnabled)
        alpha = if (isEnabled) 1f else 0.5f
    }

    fun Context.toast(text: String?) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }

    fun Context.showPermissionRequestDialog(
        title: String,
        body: String,
        callback: () -> Unit
    ) {
        AlertDialog.Builder(this).also {
            it.setTitle(title)
            it.setMessage(body)
            it.setPositiveButton("Ok") { _, _ ->
                callback()
            }
        }.create().show()
    }


}
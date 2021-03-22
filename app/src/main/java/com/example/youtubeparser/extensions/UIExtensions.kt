package com.example.youtubeparser.extensions

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide

fun AppCompatActivity.showToastLong(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun ImageView.loadImage(url: String){
    Glide.with(context)
        .load(url)
        .into(this)
}
package com.example.mvvmpractice.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("load")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view)
        .load(url)
        .placeholder(ColorDrawable(Color.DKGRAY))
        .into(view)
}
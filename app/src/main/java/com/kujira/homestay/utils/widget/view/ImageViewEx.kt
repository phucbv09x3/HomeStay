package com.kujira.homestay.utils.widget.view

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


/**
 * Created by Created by Phucbv on 5/2021
 */
@BindingAdapter("url")
fun setImageViewResource(imageView: ImageView, url: String) {
    Glide.with(imageView.context).load(url)
        .into(imageView)
}
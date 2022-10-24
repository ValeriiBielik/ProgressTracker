package com.bielik.progresstracker.utils.extensions

import androidx.appcompat.widget.AppCompatImageView
import com.bielik.progresstracker.R
import com.bumptech.glide.Glide

fun AppCompatImageView.loadCircleUserIcon(uri: String?) {
    Glide.with(this.context)
        .load(uri)
        .placeholder(R.drawable.ic_user)
        .circleCrop()
        .into(this)
}
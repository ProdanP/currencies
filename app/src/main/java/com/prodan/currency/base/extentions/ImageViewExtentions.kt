package com.prodan.currency.base.extentions

import android.annotation.SuppressLint
import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions

@SuppressLint("CheckResult")
fun ImageView.loadWithGlideCircle(url: String, context: Context) {
    val options = RequestOptions()
        .timeout(100000)
        .priority(Priority.HIGH)
    options.override(128, 80)
  //  options.circleCrop()

    Glide.with(context).load(url).apply(options).into(this)
}

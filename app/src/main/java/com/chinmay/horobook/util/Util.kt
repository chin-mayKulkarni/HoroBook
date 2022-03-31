package com.chinmay.horobook.util

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chinmay.horobook.R
import com.chinmay.horobook.view.SongsListFragment.SongsListAdapter

fun getProgressDrawable(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

fun displayPopUp(context: Context, header: String, text: String): AlertDialog {
    //return showDialog(header, text, context)
    val builder = AlertDialog.Builder(context)
    builder.setTitle(header)
    builder.setMessage(text)
    builder.setPositiveButton("OK") { dialogInterface, which ->

    }

    return builder.create().apply {
        setCancelable(false)
        show()
    }
}


fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable) {
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.ic_dashboard_black_24dp)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}
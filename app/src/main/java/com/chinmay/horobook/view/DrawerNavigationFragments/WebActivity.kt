package com.chinmay.horobook.view.DrawerNavigationFragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.chinmay.horobook.R
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)

        val url = intent.getStringExtra("url")

        url?.let { Log.d("WebActivity", it) }

        if (url != null) {
            web_view.webViewClient = WebViewClient()
            web_view.loadUrl(url)
            web_view.settings.loadsImagesAutomatically = true
            web_view.settings.javaScriptEnabled = true
            web_view.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

        }
    }
}
package com.memeusix.budgetbuddy.components

import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun ImageWebView(image: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                settings.apply {
                    javaScriptEnabled = true
                    loadWithOverviewMode = true
                    useWideViewPort = true
                }
                loadUrl(image)
            }
        },
        update = { webView ->
            webView.loadUrl(image)
        },
        modifier = Modifier.fillMaxSize()
    )

}
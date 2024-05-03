package com.example.samplewebapplication

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.webkit.ValueCallback
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebViewState
import com.google.accompanist.web.WebView

@SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
@Composable
fun BaseWebView(
    state: WebViewState,
    redirectURL: String? = null,
    onRedirected: (uri: Uri) -> Unit = {},
    onCreated: ((WebView) -> Unit)? = null,
    onPageFinished: () -> Unit = {},
    onWebViewNavigating: (uri: Uri) -> Unit = {},
    checkOnNavigation: Boolean = false,
    modifier: Modifier,
    isVisible: Boolean = true,
) {
    val webViewLoading = remember { mutableStateOf(false) }
    val modifier1 = if (isVisible) {
        modifier
            .fillMaxSize()
    } else {
        Modifier
            .size(0.dp)
    }

    Box {
        WebView(
            state = state,
            client = object : AccompanistWebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    if (checkOnNavigation && request?.url != null) {
                        request.url?.let { url ->
                                    view?.loadUrl(url.toString())
                        }
                    }
                    redirectURL?.let {
                        val isRedirected = request?.url?.toString()?.contains(it) == true
                        if (isRedirected) request?.url?.let { it1 -> onRedirected(it1) }
                        return isRedirected
                    }
                    return false
                }

                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    webViewLoading.value = true
                    super.onPageStarted(view, url, favicon)
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    webViewLoading.value = false
                    if (url != null) {
                        onPageFinished()
                    }
                }

            },
            chromeClient = object : AccompanistWebChromeClient() {
                override fun onShowFileChooser(
                    webView: WebView?,
                    filePathCallback: ValueCallback<Array<Uri>>,
                    fileChooserParams: FileChooserParams?
                ): Boolean {
                    return fileChooserParams != null
                }
            },
            onCreated = {
                it.clearCache(true)
                it.accessibilityDelegate = View.AccessibilityDelegate()
                WebView.setWebContentsDebuggingEnabled(true)

                it.settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    allowFileAccess = true
                    allowContentAccess = true
                    setGeolocationEnabled(true)
                }
                it.isFocusable = true
                it.isFocusableInTouchMode = true
                /* it.requestFocus(View.FOCUS_DOWN or View.FOCUS_UP)
                 it.getSettings().setLightTouchEnabled(true)*/
                onCreated?.invoke(it)
            },
            modifier = modifier1
        )

        if (webViewLoading.value) {
           Text("Loading")
        }
    }
}

@Composable
fun HomeWebView(
    modifier: Modifier,
    state: WebViewState,
    isVisible: Boolean,
    onPageFinished: () -> Unit = {},
    onWebViewNavigating: (uri: Uri) -> Unit = {},
    onCreated: () -> Unit = {}
) {

   BaseWebView(
        state = state,
        onCreated = {
            onCreated()
        },
        modifier = modifier,
        onPageFinished = onPageFinished,
        checkOnNavigation = true,
        onWebViewNavigating = onWebViewNavigating,
        isVisible = isVisible,
    )
}
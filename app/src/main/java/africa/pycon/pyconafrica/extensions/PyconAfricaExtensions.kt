package africa.pycon.pyconafrica.extensions

import africa.pycon.pyconafrica.R
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent.Builder
import androidx.core.content.ContextCompat

fun Context.browseCustomTab(url: String?) {
    url?.let {
        Builder()
            .setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
            .build()
            .launchUrl(this, Uri.parse(it))
    }
}

//Trying something 

fun Context.openWebView(webview: WebView, link: String?, pb: ProgressBar) {
    webview.settings.javaScriptEnabled = true
    webview.settings.loadsImagesAutomatically = true
    webview.fitsSystemWindows = true
//    webview.settings.setAppCacheEnabled(true)
//    webview.settings.cacheMode = WebSettings.LOAD_DEFAULT
//    webview.settings.setAppCachePath(cacheDir.path)
    webview.webViewClient = object : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            view?.visibility = View.INVISIBLE
            pb.visibility = View.VISIBLE
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            view?.visibility = View.VISIBLE
            pb.visibility = View.INVISIBLE
        }
    }
    webview.loadUrl(link)
}

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

package pet.perpet.framework.widget.webview

import android.content.Intent
import android.net.Uri
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView

interface FileHandler {
    @Suppress("CAST_NEVER_SUCCEEDS")
    fun onShowFileChooser(
        webView: WebView?,
        ilePathCallback: ValueCallback<Array<Uri?>?>,
        fileChooserParams: WebChromeClient.FileChooserParams?
    ): Boolean

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}
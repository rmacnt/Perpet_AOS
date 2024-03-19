package pet.perpet.framework.widget.webview.url

import android.webkit.WebView

interface UrlLoader {

    fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean

    fun isUrlMatch(url: String): Boolean
}


enum class WebClientUrl(private val className: String) {
    Intent(IntentLoader::class.java.name);

    fun create(): UrlLoader {
        return Class.forName(className).newInstance() as UrlLoader
    }
}
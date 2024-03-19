package pet.perpet.framework.widget.webview.url

import android.content.Intent
import android.net.Uri
import android.view.View
import android.webkit.WebView
import pet.perpet.framework.nonnull

class IntentLoader : UrlLoader {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        try {
            view?.goUrl(url.orEmpty())
        } catch (e: Exception) {
        }
        return true
    }

    override fun isUrlMatch(url: String): Boolean {
        return url.startsWith("intent").nonnull()
    }

    //======================================================================
    // Private Methods
    //======================================================================

    private fun View.goUrl(url: String) {
        try {
            val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
            val existPackage =
                context.packageManager.getLaunchIntentForPackage(intent.`package`.nonnull())
            if (existPackage != null) {
                this.context?.startActivity(existPackage)
            } else {
                val market = Intent(Intent.ACTION_VIEW)
                market.data = Uri.parse("market://details?id=${intent.`package`}")
                context.startActivity(market)
            }
        } catch (e: Exception) {
        }
    }
}
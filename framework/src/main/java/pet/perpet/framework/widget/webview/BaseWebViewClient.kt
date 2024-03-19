package pet.perpet.framework.widget.webview

import android.app.Activity
import android.os.Build
import android.webkit.RenderProcessGoneDetail
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import pet.perpet.framework.widget.webview.url.UrlLoader

abstract class BaseWebViewClient : WebViewClient() {

    //======================================================================
    // Variables
    //======================================================================

    abstract val context: Activity?

    var behavior: ClientBehavior? = null

    private val urlLoader: ArrayList<UrlLoader> = arrayListOf()

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        behavior?.invoke(ClientBehavior.Event.ON_PAGE_FINISHED, view, url)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val url = request?.url.toString()
        try {
            return processUrlLoader(view, url)
        } catch (e: Exception) {
        }
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        try {
            return processUrlLoader(view, url)
        } catch (e: Exception) {
        }
        return super.shouldOverrideUrlLoading(view, url)
    }

    override fun onRenderProcessGone(
        view: WebView?,
        detail: RenderProcessGoneDetail?
    ): Boolean {
        val crash = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            detail?.didCrash()
        } else {
            false
        }
        context?.finish()
        return true
    }

    //======================================================================
    // Public Methods
    //======================================================================

    fun allUrlLoader(value : List<UrlLoader>?){
        urlLoader.clear()
        value?.let { urlLoader.addAll(it) }
    }

    //======================================================================
    // Private Methods
    //======================================================================

    @Throws(Exception::class)
    private fun processUrlLoader(view: WebView?, url: String?): Boolean {
        if (urlLoader.isEmpty() == true) {
            throw Exception("url loader empty")
        }

        val compatUrl = url.orEmpty()
        val loader = urlLoader.find {
            it.isUrlMatch(compatUrl)
        } ?: throw Exception("url loader not match")
        return loader.shouldOverrideUrlLoading(view, url)
    }
}
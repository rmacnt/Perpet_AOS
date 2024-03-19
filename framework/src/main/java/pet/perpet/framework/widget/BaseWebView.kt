package pet.perpet.framework.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.databinding.BindingAdapter
import pet.perpet.framework.util.Logger

class BaseWebView : WebView {

    //======================================================================
    // Constructor
    //======================================================================

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        setBackgroundColor(Color.TRANSPARENT)
        val webSettings = settings

        webSettings.domStorageEnabled = true
        webSettings.databaseEnabled = true
        webSettings.textZoom = 100
        webSettings.pluginState = WebSettings.PluginState.ON
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE

        webSettings.javaScriptEnabled = true
        setBackgroundColor(Color.TRANSPARENT)
    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun loadUrl(url: String) {
        super.loadUrl(url)
        Logger.d("BaseWebView", "loadUrl : $url")
    }

    fun loadDataWithBaseURL(
        baseUrl: String
    ) {
        super.loadDataWithBaseURL(
            "file:///android_asset/",
            baseUrl,
            "text/html",
            "UTF-8",
            null
        )
    }

    //======================================================================
    // companion
    //======================================================================

    companion object {

        @JvmStatic
        @BindingAdapter("loadUrl")
        fun setLoadUrl(view: BaseWebView, url: String) {
            Logger.d("BaseWebView", "loadUrl : $url")
            view.loadUrl(url)
        }

        @JvmStatic
        @BindingAdapter("loadDataWithBaseURL")
        fun setLoadDataWithBaseURL(view: BaseWebView, content: String) {
            view.loadDataWithBaseURL(
                content
            )
        }
    }
}
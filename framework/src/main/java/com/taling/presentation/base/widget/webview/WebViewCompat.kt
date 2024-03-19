package com.taling.presentation.base.widget.webview

import android.annotation.SuppressLint
import android.os.Build
import android.webkit.WebSettings
import android.webkit.WebView


import pet.perpet.framework.widget.webview.url.WebClientUrl

@SuppressLint("SetJavaScriptEnabled")
fun WebView.applyDefault() {
    this.setInitialScale(1)
    this.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
    this.isScrollbarFadingEnabled = false

    val settings = this.settings
    settings.javaScriptEnabled = true
    settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
    settings.builtInZoomControls = false
    settings.loadWithOverviewMode = true
    settings.useWideViewPort = true
    settings.domStorageEnabled = true

    settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
    settings.pluginState = WebSettings.PluginState.ON
    settings.setSupportZoom(true)
    settings.javaScriptCanOpenWindowsAutomatically = true

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
    }
    applyDefaultUserAgent()
}

fun WebView.applyDefaultUserAgent() {
}

fun WebView.applyVodUserAgent() {
}

fun ClientBehavior.invoke(event: ClientBehavior.Event, vararg v: Any?) {
    val behavior = this
    behavior::class.java.declaredMethods.forEach { method ->
        val anno = method.getAnnotation(OnClientBehavior::class.java)
        val anValue = anno?.value
        if (anno != null && anValue == event) {
            method.invoke(behavior, *v)
        }
    }
}


//======================================================================
// ClientBehavior
//======================================================================

interface ClientBehavior {
    enum class Event {
        ON_PAGE_FINISHED,
        CHROME_ON_RECEIVED_TITLE,
        SHOULD_OVERRIDE_URL_LOADING,
        CHROME_ON_CREATE_WINDOW,
        CHROME_ON_CLOSE_WINDOW,
        CHROME_ON_SHOW_FILE_CHOOSER
    }
}

//======================================================================
// OnClientBehavior
//======================================================================

@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER
)
annotation class OnClientBehavior(val value: ClientBehavior.Event)


//======================================================================
// WebClientConfig
//======================================================================

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
annotation class WebClientConfig(
    val urlLoader: Array<WebClientUrl>
)
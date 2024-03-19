package pet.perpet.framework.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import androidx.fragment.app.Fragment
import pet.perpet.framework.widget.webview.BaseWebChromeClient
import pet.perpet.framework.widget.webview.BaseWebViewClient
import pet.perpet.framework.widget.webview.ClientBehavior
import pet.perpet.framework.widget.webview.FragmentFileHandler
import pet.perpet.framework.widget.webview.WebClientConfig
import pet.perpet.framework.widget.webview.url.UrlLoader

abstract class AbstractWebViewPresenter(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Variables
    //======================================================================


    private var clientBehavior: ClientBehavior? = null

    private val fileHandler = object : FragmentFileHandler() {
        override val fragment: Fragment?
            get() = this@AbstractWebViewPresenter.fragment

    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onDestroy() {
        super.onDestroy()
        clientBehavior = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fileHandler.onActivityResult(requestCode, resultCode, data)
    }

    //======================================================================
    // Public Methods
    //======================================================================


    open fun goPayComplete() {
        requireActivity().finish()
    }

    open fun goPayDepositSuccessVod(id: String?) {

    }

    open fun goPayCardSuccessVod(id: String?) {

    }

    open fun goPayDepositSuccess(id: String?) {

    }

    open fun goPayCardSuccess(id: String?) {

    }



    open fun goMoveWeb(url: String) {
        Intent(Intent.ACTION_VIEW, Uri.parse(url)).run {
            activity?.startActivity(this)
        }
    }

    open fun goEventTracking(param: String?) {

    }

    open fun goEventTrackingFirebase(param: String?) {

    }

    open fun goEventTrackingAppsflyer(param: String?) {

    }

    fun setWebClientBehavior(value: ClientBehavior) {
        clientBehavior = value
    }

    open fun onAttachWebView(view: WebView) {
        view.webViewClient = onCreateWebViewClient().apply {
            this.behavior = clientBehavior
            this.allUrlLoader(findUrlLoaderSetList())
        }
        view.webChromeClient = onCreateWebChromeClient().apply {
            this.behavior = clientBehavior
        }
        addDefaultJavascriptInterface(view)
    }

    open fun onCreateWebViewClient(): BaseWebViewClient {
        return object : BaseWebViewClient() {
            override val context: Activity?
                get() = this@AbstractWebViewPresenter.activity
        }
    }

    open fun onCreateWebChromeClient(): BaseWebChromeClient {
        return BaseWebChromeClient(fileHandler)
    }

    @SuppressLint("JavascriptInterface")
    fun addDefaultJavascriptInterface(view: WebView) {
        view.addJavascriptInterface(this, "app")
    }

    //======================================================================
    // Private Methods
    //======================================================================

    private fun findUrlLoaderSetList(): List<UrlLoader>? {
        val webConfig = this::class.java.getAnnotation(WebClientConfig::class.java)
        return webConfig?.urlLoader?.map {
            it.create()
        }?.toList()
    }

}
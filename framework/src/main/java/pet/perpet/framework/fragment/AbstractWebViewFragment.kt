package pet.perpet.framework.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import pet.perpet.framework.widget.webview.applyDefault

abstract class AbstractWebViewFragment<P : AbstractWebViewPresenter> : Fragment<P>() {

    //======================================================================
    // Variables
    //======================================================================

    abstract val dependency: WebView

    //======================================================================
    // Override Methods
    //======================================================================

    @SuppressLint("SetJavaScriptEnabled", "Recycle", "JavascriptInterface")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dependency.applyDefault()
        viewmodel.onAttachWebView(dependency)
    }

    override fun onResume() {
        super.onResume()
        dependency.onResume()
    }

    override fun onPause() {
        super.onPause()
        dependency.onPause()
    }

    override fun onDestroyView() {
        try {
            dependency.destroy()
        } catch (e: Exception) {
        }
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
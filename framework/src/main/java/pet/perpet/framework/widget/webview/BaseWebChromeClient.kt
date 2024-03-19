package pet.perpet.framework.widget.webview

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Message
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.LinearLayout

class BaseWebChromeClient(private val fileHandler: FileHandler) : WebChromeClient() {

    //======================================================================
    // Variables
    //======================================================================

    var behavior: ClientBehavior? = null

    //======================================================================
    // Override Methods
    //======================================================================

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateWindow(
        view: WebView?,
        isDialog: Boolean,
        isUserGesture: Boolean,
        resultMsg: Message?
    ): Boolean {

        /**
         * childWebView를 생성하고, 해당 webView를 이용해 새 Activity를 띄우거나,
         * 외부 브라우저로 이동할 수 있도록 작업되어야합니다.
         * 예를 들어, 아래와 같은  코드를 제안할 수 있습니다.
         */
        val childView = view?.context?.let { WebView(it) };
        childView?.applyDefault()
        childView?.webChromeClient = BaseWebChromeClient(fileHandler)
        childView?.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        );
        view?.addView(childView);
        val transport: WebView.WebViewTransport = resultMsg?.obj as WebView.WebViewTransport
        transport.webView = childView;
        resultMsg.sendToTarget();
        behavior?.invoke(ClientBehavior.Event.CHROME_ON_CREATE_WINDOW, view)
        return true
    }

    override fun onCloseWindow(window: WebView?) {
        super.onCloseWindow(window)
        behavior?.invoke(ClientBehavior.Event.CHROME_ON_CLOSE_WINDOW, window)
    }

    @Suppress("CAST_NEVER_SUCCEEDS")
    override fun onShowFileChooser(
        webView: WebView?,
        ilePathCallback: ValueCallback<Array<Uri?>?>,
        fileChooserParams: FileChooserParams?
    ): Boolean {
        return fileHandler.onShowFileChooser(webView, ilePathCallback, fileChooserParams)
    }

    override fun onReceivedTitle(view: WebView?, title: String?) {
        super.onReceivedTitle(view, title)
        behavior?.invoke(ClientBehavior.Event.CHROME_ON_RECEIVED_TITLE, view, title)
    }
}
package pet.perpet.framework.widget.webview

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.fragment.app.Fragment

abstract class FragmentFileHandler : FileHandler {

    //======================================================================
    // Variables
    //======================================================================

    abstract val fragment: Fragment?

    //======================================================================
    // Variables
    //======================================================================

    private var filePathCallback: ValueCallback<Array<Uri?>?>? = null

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onShowFileChooser(
        webView: WebView?,
        ilePathCallback: ValueCallback<Array<Uri?>?>,
        fileChooserParams: WebChromeClient.FileChooserParams?
    ): Boolean {
        filePathCallback = ilePathCallback
        return onAppShowFileChooser()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                filePathCallback?.onReceiveValue(
                    WebChromeClient.FileChooserParams.parseResult(
                        resultCode,
                        data
                    )
                );
            } else {
                filePathCallback?.onReceiveValue(arrayOf(data?.data));
            }
            filePathCallback = null;
        } else {
            filePathCallback?.onReceiveValue(null);
        }
    }

    //======================================================================
    // Private Methods
    //======================================================================

    private fun onAppShowFileChooser(
    ): Boolean {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        fragment?.startActivityForResult(intent, 0)
        return true
    }
}
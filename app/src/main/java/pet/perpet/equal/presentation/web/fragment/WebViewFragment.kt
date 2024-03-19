package pet.perpet.equal.presentation.web.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import pet.perpet.data.nonnull
import pet.perpet.equal.R
import pet.perpet.equal.databinding.BaseV2WebviewFragmentBinding
import pet.perpet.equal.presentation.goHealthResult
import pet.perpet.equal.presentation.web.vewmodel.WebViewViewModel
import pet.perpet.framework.fragment.Fragment


open class WebViewFragment : Fragment<WebViewViewModel>() {
    //======================================================================
    // Variables
    //======================================================================


    lateinit var binding: BaseV2WebviewFragmentBinding

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.base_v2_webview_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = BaseV2WebviewFragmentBinding.bind(view)
        binding.model = viewmodel

        binding.wvContent.settings.setSupportZoom(true)
        binding.wvContent.settings.builtInZoomControls = false
        binding.wvContent.settings.setGeolocationEnabled(false)
        binding.wvContent.settings.databaseEnabled = true
        binding.wvContent.settings.domStorageEnabled = true
        binding.wvContent.settings.cacheMode = WebSettings.LOAD_DEFAULT
        binding.wvContent.settings.javaScriptEnabled = true
        binding.wvContent.settings.useWideViewPort = true
        binding.wvContent.setInitialScale(1)


        binding.wvContent.clearHistory()
        binding.wvContent.clearCache(true)
        binding.wvContent.clearView()

        val cookieSyncManager = CookieSyncManager.createInstance(activity)
        val cookieManager: CookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.removeSessionCookie()
        cookieSyncManager.sync()

        binding.wvContent.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url ?: return false
                if (url.toString().contains("equal.pet")) {
                    activity?.goHealthResult()
                    activity?.finish()
                    return true
                }
                return false

            }
        }

        viewmodel.getUrl {
            binding.wvContent.loadUrl(it.url.nonnull())
        }
//        viewmodel.getUrlTwo {
//            activity?.goHealthResult()
//            activity?.finish()
//        }

        super.onViewCreated(view, savedInstanceState)

    }
}
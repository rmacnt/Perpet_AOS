package pet.perpet.equal.presentation.more.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import android.webkit.WebSettings
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import pet.perpet.equal.R
import pet.perpet.equal.databinding.BaseV1WebviewFragmentBinding
import pet.perpet.equal.presentation.getScreenHeight
import pet.perpet.equal.presentation.more.viewmodel.MoreWebViewModel
import pet.perpet.framework.fragment.BottomSheetDialogFragment


class MoreWebFragment : BottomSheetDialogFragment<MoreWebViewModel>() {

    //======================================================================
    // Variables
    //======================================================================


    lateinit var binding: BaseV1WebviewFragmentBinding

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.base_v1_webview_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = BaseV1WebviewFragmentBinding.bind(view)
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


        val cookieSyncManager = CookieSyncManager.createInstance(activity)
        val cookieManager: CookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.removeSessionCookie()
        cookieSyncManager.sync()

        val bottomSheetDialog = dialog as BottomSheetDialog
        setupRatio(bottomSheetDialog)

        super.onViewCreated(view, savedInstanceState)

    }

    private fun setupRatio(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View
        val behavior = BottomSheetBehavior.from(bottomSheet)
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = getBottomSheetDialogDefaultHeight()
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getBottomSheetDialogDefaultHeight(): Int {
        return getScreenHeight(activity) * 95 / 100
    }

}
package pet.perpet.equal.presentation.setting.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import pet.perpet.equal.R
import pet.perpet.equal.databinding.BaseWebviewFragmentBinding
import pet.perpet.equal.presentation.setting.viewmodel.InfoWebViewModel
import pet.perpet.framework.fragment.Fragment

class InfoWebFragment : Fragment<InfoWebViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: BaseWebviewFragmentBinding

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.base_webview_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = BaseWebviewFragmentBinding.bind(view)
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

        super.onViewCreated(view, savedInstanceState)

    }
}
package pet.perpet.equal.presentation.more.fragment.shipping

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import pet.perpet.equal.R
import pet.perpet.equal.databinding.BottomAddressSearchFragmentBinding
import pet.perpet.equal.presentation.base.fragment.BottomSheetDelegate
import pet.perpet.equal.presentation.base.widget.BottomSheetCallback
import pet.perpet.equal.presentation.more.viewmodel.shipping.BottomAddressSearchViewModel
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.presentation.sign.Channel
import pet.perpet.equal.support.logger.AppLogger
import pet.perpet.framework.fragment.BottomSheetDialogFragment
import pet.perpet.framework.fragment.DialogFragment
import pet.perpet.framework.nonnull
import pet.perpet.framework.util.ViewUtil

class BottomAddressSearchFragment : BottomSheetDialogFragment<BottomAddressSearchViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: BottomAddressSearchFragmentBinding


    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewmodel.observeBindingNotify {
            binding.model = viewmodel
        }


        Channel.jusoClick.consumeEach {
            try {
                it?.let {
                    viewmodel.dismissToResult(it)
                }
            }catch (e: Exception){}

        }
        setStyle(androidx.fragment.app.DialogFragment.STYLE_NORMAL, R.style.DialogTheme_Trans)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_address_search_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = BottomAddressSearchFragmentBinding.bind(view)
        binding.model = viewmodel

        val bottomSheetDialog = dialog as BottomSheetDialog
        val bottomSheetBehavior = bottomSheetDialog.behavior
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

        super.onViewCreated(view, savedInstanceState)

    }

}
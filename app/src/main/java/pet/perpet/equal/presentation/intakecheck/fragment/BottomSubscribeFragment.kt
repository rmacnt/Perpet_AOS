package pet.perpet.equal.presentation.intakecheck.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.BottomSubscribeFragmentBinding
import pet.perpet.equal.presentation.intakecheck.viewmodel.BottomSubscribeViewModel
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.framework.fragment.BottomSheetDialogFragment

class BottomSubscribeFragment : BottomSheetDialogFragment<BottomSubscribeViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: BottomSubscribeFragmentBinding

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.observeBindingNotify {
            binding.model = viewmodel
        }
        setStyle(androidx.fragment.app.DialogFragment.STYLE_NORMAL, R.style.DialogTheme_Trans)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.bottom_subscribe_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = BottomSubscribeFragmentBinding.bind(view)
        binding.model = viewmodel
        viewmodel.getPetList()
        super.onViewCreated(view, savedInstanceState)

    }
}
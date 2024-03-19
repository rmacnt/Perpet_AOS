package pet.perpet.equal.presentation.intakecheck.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.BottomHealthFragmentBinding
import pet.perpet.equal.presentation.intakecheck.viewmodel.BottomHealthViewModel
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.framework.fragment.BottomSheetDialogFragment

class BottomHealthFragment : BottomSheetDialogFragment<BottomHealthViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: BottomHealthFragmentBinding

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
        return inflater.inflate(R.layout.bottom_health_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = BottomHealthFragmentBinding.bind(view)
        binding.model = viewmodel
        viewmodel.getPetList()
        super.onViewCreated(view, savedInstanceState)

    }
}
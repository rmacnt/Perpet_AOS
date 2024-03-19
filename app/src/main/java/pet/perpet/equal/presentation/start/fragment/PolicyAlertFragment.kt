package pet.perpet.equal.presentation.start.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.BottomPolicyInfoBinding
import pet.perpet.equal.databinding.BottomSignPolicyBinding
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.presentation.start.viewmodel.PolicyAlertViewModel
import pet.perpet.framework.fragment.BottomSheetDialogFragment
import pet.perpet.framework.fragment.DialogFragment

class PolicyAlertFragment : BottomSheetDialogFragment<PolicyAlertViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: BottomSignPolicyBinding


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
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sign_policy, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = BottomSignPolicyBinding.bind(view)
        binding.model = viewmodel
        super.onViewCreated(view, savedInstanceState)

    }
}
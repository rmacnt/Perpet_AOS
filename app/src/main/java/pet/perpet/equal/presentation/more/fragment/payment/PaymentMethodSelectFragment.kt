package pet.perpet.equal.presentation.more.fragment.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.PaymentMethodSelectFragmentBinding
import pet.perpet.equal.presentation.more.viewmodel.payment.PaymentMethodSelectViewModel
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.framework.fragment.Fragment

class PaymentMethodSelectFragment : Fragment<PaymentMethodSelectViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: PaymentMethodSelectFragmentBinding

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.observeBindingNotify {
            binding.model = viewmodel
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.payment_method_select_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = PaymentMethodSelectFragmentBinding.bind(view)
        binding.model = viewmodel

        super.onViewCreated(view, savedInstanceState)

    }

}
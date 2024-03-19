package pet.perpet.equal.presentation.more.fragment.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.PaymentInsertFragmentBinding
import pet.perpet.equal.presentation.more.viewmodel.payment.PaymentInsertViewModel
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.framework.fragment.Fragment

class PaymentInsertFragment : Fragment<PaymentInsertViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: PaymentInsertFragmentBinding

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
        return inflater.inflate(R.layout.payment_insert_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = PaymentInsertFragmentBinding.bind(view)
        binding.model = viewmodel

        super.onViewCreated(view, savedInstanceState)

    }

}
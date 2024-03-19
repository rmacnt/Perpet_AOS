package pet.perpet.equal.presentation.more.fragment.shipping

import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ShippingAddressRegistrationFragmentBinding
import pet.perpet.equal.presentation.more.viewmodel.shipping.ShippingAddressRegistrationViewModel
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.framework.fragment.Fragment

class ShippingAddressRegistrationFragment : Fragment<ShippingAddressRegistrationViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: ShippingAddressRegistrationFragmentBinding

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
        return inflater.inflate(R.layout.shipping_address_registration_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = ShippingAddressRegistrationFragmentBinding.bind(view)
        binding.model = viewmodel

        super.onViewCreated(view, savedInstanceState)

    }

}
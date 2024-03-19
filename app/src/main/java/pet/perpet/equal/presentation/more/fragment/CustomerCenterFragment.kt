package pet.perpet.equal.presentation.more.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zoyi.channel.plugin.android.ChannelIO
import com.zoyi.channel.plugin.android.open.config.BootConfig
import com.zoyi.channel.plugin.android.open.enumerate.BootStatus
import com.zoyi.channel.plugin.android.open.model.Profile
import com.zoyi.channel.plugin.android.open.model.User
import pet.perpet.domain.UserStore
import pet.perpet.equal.R
import pet.perpet.equal.databinding.CustomerCenterFragmentBinding
import pet.perpet.equal.presentation.more.viewmodel.CustomerCenterViewModel
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.framework.fragment.Fragment

class CustomerCenterFragment  : Fragment<CustomerCenterViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: CustomerCenterFragmentBinding

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewmodel.observeBindingNotify {
            ChannelIO.showMessenger(activity)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.customer_center_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = CustomerCenterFragmentBinding.bind(view)
        binding.model = viewmodel

        super.onViewCreated(view, savedInstanceState)

    }

}
package pet.perpet.equal.presentation.subscribe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.SubscriptionDetailFragmentBinding
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.presentation.subscribe.viewmodel.SubscriptionDetailViewModel
import pet.perpet.framework.fragment.Fragment

class SubscriptionDetailFragment : Fragment<SubscriptionDetailViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: SubscriptionDetailFragmentBinding

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
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.subscription_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = SubscriptionDetailFragmentBinding.bind(view)
        binding.model = viewmodel
        viewmodel.getLoad()

        super.onViewCreated(view, savedInstanceState)

    }

}
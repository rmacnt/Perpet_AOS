package pet.perpet.equal.presentation.subscribe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.SubscribeFragmentBinding
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.presentation.subscribe.viewmodel.SubscribeViewModel
import pet.perpet.framework.fragment.Fragment

class SubscribeDetailFragment : Fragment<SubscribeViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: SubscribeFragmentBinding

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
        return inflater.inflate(R.layout.subscribe_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = SubscribeFragmentBinding.bind(view)
        binding.model = viewmodel
        viewmodel.getTotalPrice()

        super.onViewCreated(view, savedInstanceState)

    }

}
package pet.perpet.equal.presentation.more.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemPetCardBinding
import pet.perpet.equal.databinding.OrderDetailFragmentBinding
import pet.perpet.equal.presentation.more.viewmodel.OrderDetailViewModel
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.framework.fragment.Fragment

class OrderDetailFragment : Fragment<OrderDetailViewModel>() {
    //======================================================================
    // Variables
    //======================================================================


    lateinit var binding: OrderDetailFragmentBinding

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
        return inflater.inflate(R.layout.order_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = OrderDetailFragmentBinding.bind(view)
        binding.model = viewmodel
        viewmodel.getLoad()
        super.onViewCreated(view, savedInstanceState)

    }

}
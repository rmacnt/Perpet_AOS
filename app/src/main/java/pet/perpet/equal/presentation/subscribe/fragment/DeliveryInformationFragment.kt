package pet.perpet.equal.presentation.subscribe.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pet.perpet.equal.R
import pet.perpet.equal.databinding.DeliveryInformationFragmentBinding
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.presentation.subscribe.viewmodel.DeliveryInformationViewModel
import pet.perpet.framework.fragment.v2.AbstractRecyclerViewFragment

class DeliveryInformationFragment : AbstractRecyclerViewFragment<DeliveryInformationViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: DeliveryInformationFragmentBinding

    override val recyclerView: RecyclerView
        get() = binding.recyclerview
    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.observeBindingNotify {
            binding.model = viewmodel
        }
    }

    override fun onCreateLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.delivery_information_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = DeliveryInformationFragmentBinding.bind(view)
        binding.model = viewmodel
        viewmodel.load {
            notifySupportDataSetChanged()
        }
        super.onViewCreated(view, savedInstanceState)

    }
}
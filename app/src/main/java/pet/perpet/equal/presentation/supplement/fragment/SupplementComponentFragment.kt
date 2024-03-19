package pet.perpet.equal.presentation.supplement.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import pet.perpet.equal.R
import pet.perpet.equal.databinding.SupplementComponentFragmentBinding
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.presentation.supplement.viewmodel.SupplementComponentViewModel
import pet.perpet.framework.fragment.OnTabSelectedBehavior
import pet.perpet.framework.fragment.v2.AbstractRecyclerViewFragment

class SupplementComponentFragment : AbstractRecyclerViewFragment<SupplementComponentViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: SupplementComponentFragmentBinding

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
        return LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.supplement_component_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = SupplementComponentFragmentBinding.bind(view)
        binding.model = viewmodel
        super.onViewCreated(view, savedInstanceState)

    }

}
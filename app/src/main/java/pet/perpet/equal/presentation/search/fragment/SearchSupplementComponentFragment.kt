package pet.perpet.equal.presentation.search.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pet.perpet.equal.R
import pet.perpet.equal.databinding.SearchSupplementComponentFragmentBinding
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.presentation.search.viewmodel.SearchSupplementComponentViewModel
import pet.perpet.framework.fragment.v2.AbstractRecyclerViewFragment

class SearchSupplementComponentFragment : AbstractRecyclerViewFragment<SearchSupplementComponentViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: SearchSupplementComponentFragmentBinding

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
        return inflater.inflate(R.layout.search_supplement_component_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = SearchSupplementComponentFragmentBinding.bind(view)
        binding.model = viewmodel
        super.onViewCreated(view, savedInstanceState)

    }

}
package pet.perpet.equal.presentation.search.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pet.perpet.equal.R
import pet.perpet.equal.databinding.SearchSupplementFragmentBinding
import pet.perpet.equal.presentation.search.viewmodel.SearchSupplementViewModel
import pet.perpet.framework.fragment.v2.AbstractSwipePagerRecyclerViewFragment
import pet.perpet.framework.widget.recyclerview.SwipeRefreshRecyclerView

class SearchSupplementFragment : AbstractSwipePagerRecyclerViewFragment<SearchSupplementViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: SearchSupplementFragmentBinding

    override val recyclerView: SwipeRefreshRecyclerView
        get() = binding.recyclerview

    override val dependencyEmptyView: View
        get() = binding.tvEmpty

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_supplement_fragment, container, false)
    }

    override fun onCreateLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = SearchSupplementFragmentBinding.bind(view)
        binding.model = viewmodel
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerview.setSwipeRefreshLayoutEnable(true)
        binding.recyclerview.setRefreshing(true)
    }

}
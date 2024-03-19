package pet.perpet.equal.presentation.search.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.search.SearchSimple
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSearchSimpleRecentBinding
import pet.perpet.equal.presentation.search.viewmodel.SearchSimpleRecentViewModel
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder

class SearchSimpleRecentViewHolder (viewGroup: ViewGroup) :
    RecyclerViewHolder<SearchSimple>(viewGroup, R.layout.item_search_simple_recent) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSearchSimpleRecentBinding = ItemSearchSimpleRecentBinding.bind(itemView)

    val viewmodel: SearchSimpleRecentViewModel = SearchSimpleRecentViewModel()

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: SearchSimple?) {
        viewmodel.model = t
        binding.model = viewmodel
    }
}
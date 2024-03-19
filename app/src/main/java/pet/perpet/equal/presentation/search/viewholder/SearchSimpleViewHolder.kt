package pet.perpet.equal.presentation.search.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.search.SearchSimple
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSearchSimpleBinding
import pet.perpet.equal.presentation.search.viewmodel.SearchSimpleViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class SearchSimpleViewHolder(viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<SearchSimple>(viewGroup, R.layout.item_search_simple) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSearchSimpleBinding = ItemSearchSimpleBinding.bind(itemView)

    val viewmodel: SearchSimpleViewModel = SearchSimpleViewModel()

    init {
        viewmodel.notify {
            sendEvent(hashCode())
        }
    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: SearchSimple?) {
        viewmodel.model = t
        binding.model = viewmodel
    }
}
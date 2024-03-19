package pet.perpet.equal.presentation.search.viewholder

import android.util.Log
import android.view.ViewGroup
import pet.perpet.domain.model.search.SearchTag
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSearchTagBinding
import pet.perpet.equal.presentation.search.viewmodel.ItemSearchTagViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class SearchTagViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<SearchTag>(viewGroup, R.layout.item_search_tag) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSearchTagBinding = ItemSearchTagBinding.bind(itemView)

    val viewmodel: ItemSearchTagViewModel = ItemSearchTagViewModel()

    init {
        viewmodel.notify {
            sendEvent(hashCode())
        }
    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: SearchTag?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }
}
package pet.perpet.equal.presentation.search.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.search.SearchProduct
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSearchProductBinding
import pet.perpet.equal.presentation.search.viewmodel.ItemSearchProductViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class SearchProductViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<SearchProduct>(viewGroup, R.layout.item_search_product) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSearchProductBinding = ItemSearchProductBinding.bind(itemView)

    val viewmodel: ItemSearchProductViewModel = ItemSearchProductViewModel()

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: SearchProduct?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }
}
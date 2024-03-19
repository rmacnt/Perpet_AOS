package pet.perpet.equal.presentation.search.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.search.Ingredient
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSearchCodeViewBinding
import pet.perpet.equal.presentation.search.viewmodel.ItemCodeViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class ItemCodeViewHolder(viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<Ingredient>(viewGroup, R.layout.item_search_code_view) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSearchCodeViewBinding = ItemSearchCodeViewBinding.bind(itemView)

    val viewmodel: ItemCodeViewModel = ItemCodeViewModel()

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: Ingredient?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }
}
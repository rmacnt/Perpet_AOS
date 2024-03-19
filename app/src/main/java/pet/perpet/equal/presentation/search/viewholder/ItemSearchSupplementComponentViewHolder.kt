package pet.perpet.equal.presentation.search.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.search.Ingredient
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSearchSupplementComponentsBinding
import pet.perpet.equal.presentation.search.viewmodel.ItemSearchSupplementComponentViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class ItemSearchSupplementComponentViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<Ingredient>(
        viewGroup,
        R.layout.item_search_supplement_components
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSearchSupplementComponentsBinding =
        ItemSearchSupplementComponentsBinding.bind(itemView)

    val viewmodel: ItemSearchSupplementComponentViewModel = ItemSearchSupplementComponentViewModel()



    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: Ingredient?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }
}
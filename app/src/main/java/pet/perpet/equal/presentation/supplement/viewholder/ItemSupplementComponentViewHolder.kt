package pet.perpet.equal.presentation.supplement.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.medical.Ingredient
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSupplementComponentBinding
import pet.perpet.equal.presentation.supplement.viewmodel.ItemSupplementComponentViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class ItemSupplementComponentViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<Ingredient>(
        viewGroup,
        R.layout.item_supplement_component
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSupplementComponentBinding =
        ItemSupplementComponentBinding.bind(itemView)

    val viewmodel: ItemSupplementComponentViewModel = ItemSupplementComponentViewModel()



    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: Ingredient?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }
}
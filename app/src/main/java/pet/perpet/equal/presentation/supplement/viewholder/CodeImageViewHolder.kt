package pet.perpet.equal.presentation.supplement.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.address.Juso
import pet.perpet.domain.model.medical.Ingredient
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemAddressSearchBinding
import pet.perpet.equal.databinding.ItemCodeImageBinding
import pet.perpet.equal.presentation.more.viewmodel.shipping.ItemAddressViewModel
import pet.perpet.equal.presentation.supplement.viewmodel.CodeImageViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class CodeImageViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<Ingredient>(viewGroup, R.layout.item_code_image) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemCodeImageBinding = ItemCodeImageBinding.bind(itemView)

    val viewmodel: CodeImageViewModel = CodeImageViewModel()

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: Ingredient?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }
}
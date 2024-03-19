package pet.perpet.equal.presentation.supplement.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.medical.Ingredient
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemCodeViewBinding
import pet.perpet.equal.presentation.supplement.viewmodel.CodeViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class CodeViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<Ingredient>(viewGroup, R.layout.item_code_view) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemCodeViewBinding = ItemCodeViewBinding.bind(itemView)

    val viewmodel: CodeViewModel = CodeViewModel()

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: Ingredient?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }
}
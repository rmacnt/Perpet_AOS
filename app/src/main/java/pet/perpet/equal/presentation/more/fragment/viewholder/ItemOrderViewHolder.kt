package pet.perpet.equal.presentation.more.fragment.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.order.Inventory
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemOrderBinding
import pet.perpet.equal.presentation.more.viewmodel.ItemOrderViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class ItemOrderViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<Inventory>(viewGroup, R.layout.item_order) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemOrderBinding = ItemOrderBinding.bind(itemView)

    val viewmodel: ItemOrderViewModel = ItemOrderViewModel()

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: Inventory?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }
}
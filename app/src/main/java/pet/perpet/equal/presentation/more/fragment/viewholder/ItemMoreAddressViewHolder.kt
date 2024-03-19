package pet.perpet.equal.presentation.more.fragment.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.address.ListAddress
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemAddrInfoBinding
import pet.perpet.equal.presentation.more.viewmodel.shipping.ItemMoreAddressViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class ItemMoreAddressViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<ListAddress>(viewGroup, R.layout.item_addr_info) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemAddrInfoBinding = ItemAddrInfoBinding.bind(itemView)

    val viewmodel:  ItemMoreAddressViewModel = ItemMoreAddressViewModel()

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: ListAddress?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }
}
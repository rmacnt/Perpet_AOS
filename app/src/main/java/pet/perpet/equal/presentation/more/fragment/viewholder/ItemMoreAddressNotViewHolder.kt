package pet.perpet.equal.presentation.more.fragment.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.address.ListAddress
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemAddrInfoNotBinding
import pet.perpet.equal.presentation.more.viewmodel.shipping.ItemMoreAddressNotViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class ItemMoreAddressNotViewHolder(viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<ListAddress>(viewGroup, R.layout.item_addr_info_not) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemAddrInfoNotBinding = ItemAddrInfoNotBinding.bind(itemView)

    val viewmodel: ItemMoreAddressNotViewModel = ItemMoreAddressNotViewModel()

    init {
        viewmodel.notify {
            sendEvent(hashCode())
        }
    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: ListAddress?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }
}
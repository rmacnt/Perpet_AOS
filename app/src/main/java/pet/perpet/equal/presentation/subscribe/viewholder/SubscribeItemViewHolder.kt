package pet.perpet.equal.presentation.subscribe.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.address.ListAddress
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSubscribeAddressBinding
import pet.perpet.equal.presentation.subscribe.viewmodel.SubscribeItemViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class SubscribeItemViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<ListAddress>(viewGroup, R.layout.item_subscribe_address) {

    //======================================================================
    // Variables
    //======================================================================

    val viewmodel: SubscribeItemViewModel = object : SubscribeItemViewModel() {
    }

    val binding: ItemSubscribeAddressBinding = ItemSubscribeAddressBinding.bind(itemView)

    init {
        viewmodel.notify {
            viewmodel.model = it
            sendEvent(hashCode())
        }
    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: ListAddress?) {
        viewmodel.model = t
        binding.model = viewmodel
    }
}
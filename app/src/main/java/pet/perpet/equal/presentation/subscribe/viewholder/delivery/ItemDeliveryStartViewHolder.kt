package pet.perpet.equal.presentation.subscribe.viewholder.delivery

import android.view.ViewGroup
import pet.perpet.domain.model.tracker.TrackerProgresse
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemDeliveryStartBinding
import pet.perpet.equal.presentation.subscribe.viewmodel.delivery.ItemDeliveryStartViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class ItemDeliveryStartViewHolder(viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<TrackerProgresse>(
        viewGroup,
        R.layout.item_delivery_start
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemDeliveryStartBinding =
        ItemDeliveryStartBinding.bind(itemView)

    val viewmodel: ItemDeliveryStartViewModel = ItemDeliveryStartViewModel()

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: TrackerProgresse?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }

}
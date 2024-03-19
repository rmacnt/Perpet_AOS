package pet.perpet.equal.presentation.subscribe.viewholder.delivery

import android.view.ViewGroup
import pet.perpet.domain.model.tracker.TrackerProgresse
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemDeliveryStartOneBinding
import pet.perpet.equal.presentation.subscribe.viewmodel.delivery.ItemDeliveryStartOneViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class ItemDeliveryStartOneViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<TrackerProgresse>(
        viewGroup,
        R.layout.item_delivery_start_one
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemDeliveryStartOneBinding =
        ItemDeliveryStartOneBinding.bind(itemView)

    val viewmodel: ItemDeliveryStartOneViewModel = ItemDeliveryStartOneViewModel()

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: TrackerProgresse?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }

}
package pet.perpet.equal.presentation.subscribe.viewholder.delivery

import android.view.ViewGroup
import pet.perpet.domain.model.tracker.TrackerProgresse
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemDeliveryMiddleStartBinding
import pet.perpet.equal.presentation.subscribe.viewmodel.delivery.ItemDeliveryMiddleStartViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class ItemDeliveryMiddleStartViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<TrackerProgresse>(
        viewGroup,
        R.layout.item_delivery_middle_start
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemDeliveryMiddleStartBinding =
        ItemDeliveryMiddleStartBinding.bind(itemView)

    val viewmodel: ItemDeliveryMiddleStartViewModel = ItemDeliveryMiddleStartViewModel()

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: TrackerProgresse?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }

}
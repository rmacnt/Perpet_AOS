package pet.perpet.equal.presentation.subscribe.viewholder.delivery

import android.view.ViewGroup
import pet.perpet.domain.model.tracker.TrackerProgresse
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemDeliveryMiddleBinding
import pet.perpet.equal.presentation.subscribe.viewmodel.delivery.ItemDeliveryMiddleViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class ItemDeliveryMiddleViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<TrackerProgresse>(
        viewGroup,
        R.layout.item_delivery_middle
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemDeliveryMiddleBinding =
        ItemDeliveryMiddleBinding.bind(itemView)

    val viewmodel: ItemDeliveryMiddleViewModel = ItemDeliveryMiddleViewModel()

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: TrackerProgresse?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }

}
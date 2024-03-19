package pet.perpet.equal.presentation.subscribe.viewholder.delivery

import android.view.ViewGroup
import pet.perpet.domain.model.tracker.TrackerProgresse
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemDeliveryEndBinding
import pet.perpet.equal.presentation.subscribe.viewmodel.delivery.ItemDeliveryEndViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class ItemDeliveryEndViewHolder(viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<TrackerProgresse>(
        viewGroup,
        R.layout.item_delivery_end
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemDeliveryEndBinding =
        ItemDeliveryEndBinding.bind(itemView)

    val viewmodel: ItemDeliveryEndViewModel = ItemDeliveryEndViewModel()

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: TrackerProgresse?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }

}
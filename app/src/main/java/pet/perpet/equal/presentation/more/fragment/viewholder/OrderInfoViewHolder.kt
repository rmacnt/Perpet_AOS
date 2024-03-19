package pet.perpet.equal.presentation.more.fragment.viewholder

import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemOrderInfoBinding
import pet.perpet.equal.presentation.more.model.OrderInfo
import pet.perpet.equal.presentation.more.viewmodel.OrderInfoViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class OrderInfoViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<OrderInfo>(viewGroup, R.layout.item_order_info) {

    //======================================================================
    // Variables
    //======================================================================

    val viewmodel: OrderInfoViewModel = object : OrderInfoViewModel() {

    }

    val binding: ItemOrderInfoBinding = ItemOrderInfoBinding.bind(itemView)

    init {
        viewmodel.notify {
            sendEvent(hashCode())
        }
    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: OrderInfo?) {
        viewmodel.model = t
        binding.model = viewmodel
    }
}
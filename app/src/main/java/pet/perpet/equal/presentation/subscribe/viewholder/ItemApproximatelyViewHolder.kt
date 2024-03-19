package pet.perpet.equal.presentation.subscribe.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.product.Product
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemApproximatelyBinding
import pet.perpet.equal.presentation.subscribe.SubscribeChannel
import pet.perpet.equal.presentation.subscribe.viewmodel.ItemApproximatelyVIewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class ItemApproximatelyViewHolder(viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<Product>(
        viewGroup,
        R.layout.item_approximately
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemApproximatelyBinding =
        ItemApproximatelyBinding.bind(itemView)

    val viewmodel: ItemApproximatelyVIewModel = ItemApproximatelyVIewModel()

    init {
        viewmodel.notify { product ->
            viewmodel.model = product
            binding.model = viewmodel
            SubscribeChannel.productClick.send(product)
        }
    }


    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: Product?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }

}
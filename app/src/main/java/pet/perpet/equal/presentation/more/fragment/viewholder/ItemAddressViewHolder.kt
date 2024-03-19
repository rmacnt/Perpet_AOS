package pet.perpet.equal.presentation.more.fragment.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.address.Juso
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemAddressSearchBinding
import pet.perpet.equal.presentation.more.viewmodel.shipping.ItemAddressViewModel
import pet.perpet.equal.presentation.sign.Channel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class ItemAddressViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<Juso>(viewGroup, R.layout.item_address_search) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemAddressSearchBinding = ItemAddressSearchBinding.bind(itemView)

    val viewmodel: ItemAddressViewModel = ItemAddressViewModel()

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: Juso?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }
}
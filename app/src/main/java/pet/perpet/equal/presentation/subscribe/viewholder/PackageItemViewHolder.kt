package pet.perpet.equal.presentation.subscribe.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.product.Product
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemPackageBinding
import pet.perpet.equal.presentation.subscribe.viewmodel.PackageItemViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class PackageItemViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<Product>(
        viewGroup,
        R.layout.item_package
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemPackageBinding =
        ItemPackageBinding.bind(itemView)

    val viewmodel: PackageItemViewModel = PackageItemViewModel()


    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: Product?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }

}
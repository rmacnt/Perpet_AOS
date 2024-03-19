package pet.perpet.equal.presentation.examination.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.medical.Package
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemPackageCodeBinding
import pet.perpet.equal.presentation.examination.viewmodel.PackageListItemViewModel
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder

class PackageListItemViewHolder (viewGroup: ViewGroup) :
    RecyclerViewHolder<Package>(viewGroup, R.layout.item_package_code) {

    //======================================================================
    // Variables
    //======================================================================

    val viewmodel: PackageListItemViewModel = object : PackageListItemViewModel() {
    }

    val binding: ItemPackageCodeBinding = ItemPackageCodeBinding.bind(itemView)

    init {
        viewmodel.context = {
            context
        }
    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: Package?) {
        viewmodel.model = t
        binding.model = viewmodel
    }
}
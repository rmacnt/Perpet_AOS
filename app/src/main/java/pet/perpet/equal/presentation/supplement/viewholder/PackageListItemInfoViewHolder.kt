package pet.perpet.equal.presentation.supplement.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.medical.Package
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemPackageCodeBinding
import pet.perpet.equal.databinding.ItemPackageInfoCodeBinding
import pet.perpet.equal.presentation.examination.viewmodel.PackageListItemViewModel
import pet.perpet.equal.presentation.supplement.viewmodel.PackageListItemInfoViewModel
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder

class PackageListItemInfoViewHolder (viewGroup: ViewGroup) :
    RecyclerViewHolder<Package>(viewGroup, R.layout.item_package_info_code) {

    //======================================================================
    // Variables
    //======================================================================

    val viewmodel: PackageListItemInfoViewModel = object : PackageListItemInfoViewModel() {
    }

    val binding: ItemPackageInfoCodeBinding = ItemPackageInfoCodeBinding.bind(itemView)

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
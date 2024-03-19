package pet.perpet.equal.presentation.intakecheck.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.pet.Pet
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemBottomIntakeBinding
import pet.perpet.equal.presentation.intakecheck.viewmodel.ItemIntakeViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class ItemIntakeViewHolder(viewGroup: ViewGroup? , private val type: Boolean) :
    BaseRecyclerViewHolder<Pet>(viewGroup, R.layout.item_bottom_intake) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemBottomIntakeBinding = ItemBottomIntakeBinding.bind(itemView)

    val viewmodel: ItemIntakeViewModel = ItemIntakeViewModel()

    init {
        viewmodel.notify {
            sendEvent(hashCode())
        }
    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: Pet?) {
        super.onRefresh(t)
        viewmodel.model = t
        viewmodel.type = type
        binding.model = viewmodel
    }
}
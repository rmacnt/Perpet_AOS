package pet.perpet.equal.presentation.intakecheck.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.intake.IntakeCare
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemIntakeCareBinding
import pet.perpet.equal.presentation.intakecheck.viewmodel.ItemCareViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class ItemCareViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<IntakeCare>(viewGroup, R.layout.item_intake_care) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemIntakeCareBinding = ItemIntakeCareBinding.bind(itemView)

    val viewmodel: ItemCareViewModel = ItemCareViewModel()

    init {
        viewmodel.notify {
            sendEvent(hashCode())
        }
    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: IntakeCare?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }
}
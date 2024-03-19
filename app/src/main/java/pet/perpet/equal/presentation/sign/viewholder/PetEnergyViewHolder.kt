package pet.perpet.equal.presentation.sign.viewholder

import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSignPetEnergyBinding
import pet.perpet.equal.presentation.sign.model.PetEnergy
import pet.perpet.equal.presentation.sign.viewmodel.PetEnergyViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class PetEnergyViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<PetEnergy>(
        viewGroup,
        R.layout.item_sign_pet_energy
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSignPetEnergyBinding =
        ItemSignPetEnergyBinding.bind(itemView)

    val viewmodel: PetEnergyViewModel = PetEnergyViewModel()

    init {

        viewmodel.notifyAsk {
            item.petAsk = it
            viewmodel.model = item
            binding.model = viewmodel
        }

        viewmodel.notify {
            item.error = false
            if (item.conditionsCode == it.toInt()) {
                item.conditionsCode = -1
            } else {
                item.conditionsCode = it.toInt()
            }
            viewmodel.model = item
            binding.model = viewmodel
            sendEvent(hashCode())
        }
    }


    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: PetEnergy?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }

}
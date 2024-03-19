package pet.perpet.equal.presentation.sign.viewholder

import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSignPetNeuteringBinding
import pet.perpet.equal.presentation.sign.model.PetNeutering
import pet.perpet.equal.presentation.sign.viewmodel.PetNeuteringViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class PetNeuteringViewHolder(viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<PetNeutering>(
        viewGroup,
        R.layout.item_sign_pet_neutering
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSignPetNeuteringBinding =
        ItemSignPetNeuteringBinding.bind(itemView)

    val viewmodel: PetNeuteringViewModel = PetNeuteringViewModel()

    init {

        viewmodel.notifyAsk {
            item.petAsk = it
            binding.model = viewmodel
        }

        viewmodel.notify {
            item.error = false
            if (item.neutralizationCode == it) {
                item.neutralizationCode = -1
            } else {
                item.neutralizationCode = it
            }
            viewmodel.model = item
            binding.model = viewmodel
            sendEvent(hashCode())
        }
    }


    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: PetNeutering?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }
}
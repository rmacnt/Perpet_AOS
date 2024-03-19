package pet.perpet.equal.presentation.sign.viewholder

import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSignPetLivingTogetherBinding
import pet.perpet.equal.presentation.sign.model.PetLivingTogether
import pet.perpet.equal.presentation.sign.viewmodel.PetLivingTogetherViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class PetLivingTogetherViewHolder  (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<PetLivingTogether>(
        viewGroup,
        R.layout.item_sign_pet_living_together
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSignPetLivingTogetherBinding =
        ItemSignPetLivingTogetherBinding.bind(itemView)

    val viewmodel: PetLivingTogetherViewModel = PetLivingTogetherViewModel()

    init {

        viewmodel.notifyAsk {
            item.petAsk = it
            binding.model = viewmodel
        }

        viewmodel.notify {
            item.error = false
            if (item.relationshipCode == it.toInt()) {
                item.relationshipCode = -1
            } else {
                item.relationshipCode = it.toInt()
            }
            viewmodel.model = item
            binding.model = viewmodel
            sendEvent(hashCode())
        }
    }


    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: PetLivingTogether?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }

}
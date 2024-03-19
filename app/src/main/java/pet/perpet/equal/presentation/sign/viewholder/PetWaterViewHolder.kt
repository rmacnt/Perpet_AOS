package pet.perpet.equal.presentation.sign.viewholder

import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSignPetWaterBinding
import pet.perpet.equal.presentation.sign.model.PetWater
import pet.perpet.equal.presentation.sign.viewmodel.PetWaterViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class PetWaterViewHolder  (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<PetWater>(
        viewGroup,
        R.layout.item_sign_pet_water
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSignPetWaterBinding =
        ItemSignPetWaterBinding.bind(itemView)

    val viewmodel: PetWaterViewModel = PetWaterViewModel()

    init {

        viewmodel.notifyAsk {
            item.petAsk = it
            viewmodel.model = item
            binding.model = viewmodel
        }

        viewmodel.notify {
            item.error  = false
            if (item.drinkingAmountCode == it.toInt()) {
                item.drinkingAmountCode = -1
            } else {
                item.drinkingAmountCode = it.toInt()
            }
            viewmodel.model = item
            binding.model = viewmodel
            sendEvent(hashCode())
        }
    }


    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: PetWater?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }

}
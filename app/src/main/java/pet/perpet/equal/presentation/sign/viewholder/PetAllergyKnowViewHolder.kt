package pet.perpet.equal.presentation.sign.viewholder

import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSignPetAllergyKnowBinding
import pet.perpet.equal.presentation.sign.model.PetAllergyKnow
import pet.perpet.equal.presentation.sign.viewmodel.PetAllergyKnowViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class PetAllergyKnowViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<PetAllergyKnow>(
        viewGroup,
        R.layout.item_sign_pet_allergy_know
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSignPetAllergyKnowBinding =
        ItemSignPetAllergyKnowBinding.bind(itemView)

    val viewmodel: PetAllergyKnowViewModel = PetAllergyKnowViewModel()

    init {

        viewmodel.notifyAsk {
            item.petAsk = it
            viewmodel.model = item
            binding.model = viewmodel
        }

        viewmodel.notify {
            item.error = false
            if (item.howToKnowAllergyCode == it.toInt()) {
                item.howToKnowAllergyCode = -1
            } else {
                item.howToKnowAllergyCode = it.toInt()
            }
            viewmodel.model = item
            binding.model = viewmodel
            sendEvent(hashCode())
        }
    }


    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: PetAllergyKnow?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }

}
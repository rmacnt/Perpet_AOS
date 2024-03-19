package pet.perpet.equal.presentation.sign.viewholder

import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSignPetCatEarBinding
import pet.perpet.equal.presentation.sign.model.PetCatEar
import pet.perpet.equal.presentation.sign.viewmodel.PetCatEarViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class PetCatEarViewHolder  (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<PetCatEar>(
        viewGroup,
        R.layout.item_sign_pet_cat_ear
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSignPetCatEarBinding =
        ItemSignPetCatEarBinding.bind(itemView)

    val viewmodel: PetCatEarViewModel = PetCatEarViewModel()

    init {
        viewmodel.notify {
            item.petEar = it
            item.error = false
            viewmodel.model = item
            binding.model = viewmodel
            sendEvent(hashCode())
        }
    }
    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: PetCatEar?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }
}
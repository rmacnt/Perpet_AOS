package pet.perpet.equal.presentation.sign.viewholder

import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSignPetSelectBinding
import pet.perpet.equal.presentation.sign.model.PetSelect
import pet.perpet.equal.presentation.sign.viewmodel.PetSelectViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class PetSelectViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<PetSelect>(
        viewGroup,
        R.layout.item_sign_pet_select
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSignPetSelectBinding =
        ItemSignPetSelectBinding.bind(itemView)

    val viewmodel: PetSelectViewModel = PetSelectViewModel()

    init {
        viewmodel.notify {
            item.petSelect = it
            viewmodel.model = item
            binding.model = viewmodel
            sendEvent(hashCode())
        }
    }


    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: PetSelect?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }
}
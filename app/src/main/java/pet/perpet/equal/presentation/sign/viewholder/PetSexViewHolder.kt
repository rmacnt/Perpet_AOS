package pet.perpet.equal.presentation.sign.viewholder

import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSignPetSexBinding
import pet.perpet.equal.presentation.sign.model.PetSex
import pet.perpet.equal.presentation.sign.viewmodel.PetSexViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class PetSexViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<PetSex>(
        viewGroup,
        R.layout.item_sign_pet_sex
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSignPetSexBinding =
        ItemSignPetSexBinding.bind(itemView)

    val viewmodel: PetSexViewModel = PetSexViewModel()

    init {
        viewmodel.notify {
            item.petSex = it
            viewmodel.model = item
            binding.model = viewmodel
            sendEvent(hashCode())
        }
        viewmodel.notifyAsk {
            item.petAsk = it
            binding.model = viewmodel
        }
    }


    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: PetSex?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }
}
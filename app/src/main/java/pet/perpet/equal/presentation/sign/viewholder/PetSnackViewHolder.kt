package pet.perpet.equal.presentation.sign.viewholder

import android.util.Log
import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSignPetSnackBinding
import pet.perpet.equal.presentation.sign.model.PetSnack
import pet.perpet.equal.presentation.sign.viewmodel.PetSnackViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class PetSnackViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<PetSnack>(
        viewGroup,
        R.layout.item_sign_pet_snack
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSignPetSnackBinding =
        ItemSignPetSnackBinding.bind(itemView)

    val viewmodel: PetSnackViewModel = PetSnackViewModel()

    init {
        viewmodel.notifyAsk {
            item.petAsk = it
            viewmodel.model = item
            binding.model = viewmodel
        }

        viewmodel.notify {
            item.snack = it.toInt()
            viewmodel.model = item
            binding.model = viewmodel
            sendEvent(hashCode())
        }
    }


    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: PetSnack?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }

}
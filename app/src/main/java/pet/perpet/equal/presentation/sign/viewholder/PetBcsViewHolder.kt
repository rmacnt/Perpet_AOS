package pet.perpet.equal.presentation.sign.viewholder

import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSignPetBcsBinding
import pet.perpet.equal.presentation.sign.model.PetBcs
import pet.perpet.equal.presentation.sign.viewmodel.PetBcsViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class PetBcsViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<PetBcs>(
        viewGroup,
        R.layout.item_sign_pet_bcs
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSignPetBcsBinding =
        ItemSignPetBcsBinding.bind(itemView)

    val viewmodel: PetBcsViewModel = PetBcsViewModel()

    init {
        viewmodel.notify {
            item.petBcsCode = it
            item.error = false
            item.petBcsAllView = true
            viewmodel.model = item
            binding.model = viewmodel
            sendEvent(hashCode())
        }

        viewmodel.notifyAsk {
            item.petAsk = it
            viewmodel.model = item
            binding.model = viewmodel
        }

        viewmodel.notifyChange {
            item.petBcsCode = -1
            item.petBcsAllView = it
            viewmodel.model = item
            binding.model = viewmodel
        }
    }


    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: PetBcs?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }
}
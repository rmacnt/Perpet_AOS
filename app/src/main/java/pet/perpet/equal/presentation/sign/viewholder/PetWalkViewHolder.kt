package pet.perpet.equal.presentation.sign.viewholder

import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSignPetWalkBinding
import pet.perpet.equal.presentation.sign.model.PetWalk
import pet.perpet.equal.presentation.sign.viewmodel.PetWalkViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class PetWalkViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<PetWalk>(
        viewGroup,
        R.layout.item_sign_pet_walk
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSignPetWalkBinding =
        ItemSignPetWalkBinding.bind(itemView)

    val viewmodel: PetWalkViewModel = PetWalkViewModel()

    init {

        viewmodel.notifyAsk {
            item.petAsk = it
            viewmodel.model = item
            binding.model = viewmodel
        }

        viewmodel.notify {
            item.error = false
            if (item.walkCode == it.toInt()) {
                item.walkCode = -1
            } else {
                item.walkCode = it.toInt()
            }
            viewmodel.model = item
            binding.model = viewmodel
            sendEvent(hashCode())
        }
    }


    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: PetWalk?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }

}
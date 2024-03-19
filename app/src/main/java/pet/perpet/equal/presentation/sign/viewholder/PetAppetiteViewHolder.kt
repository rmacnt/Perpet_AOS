package pet.perpet.equal.presentation.sign.viewholder

import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSignPetAppetiteBinding
import pet.perpet.equal.presentation.sign.model.PetAppetite
import pet.perpet.equal.presentation.sign.viewmodel.PetAppetiteViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class PetAppetiteViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<PetAppetite>(
        viewGroup,
        R.layout.item_sign_pet_appetite
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSignPetAppetiteBinding =
        ItemSignPetAppetiteBinding.bind(itemView)

    val viewmodel: PetAppetiteViewModel = PetAppetiteViewModel()

    init {

        viewmodel.notifyAsk {
            item.petAsk = it
            viewmodel.model = item
            binding.model = viewmodel
        }

        viewmodel.notify {
            if(it != "null") {
                item.error = false
                if (item.appetiteChangeCode == it.toInt()) {
                    item.appetiteChangeCode = -1
                } else {
                    item.appetiteChangeCode = it.toInt()
                }
                viewmodel.model = item
                binding.model = viewmodel
                sendEvent(hashCode())
            }
        }
    }


    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: PetAppetite?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }

}
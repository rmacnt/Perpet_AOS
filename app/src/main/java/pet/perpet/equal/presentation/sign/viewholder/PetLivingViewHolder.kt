package pet.perpet.equal.presentation.sign.viewholder

import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSignPetLivingBinding
import pet.perpet.equal.presentation.getString
import pet.perpet.equal.presentation.sign.model.PetLiving
import pet.perpet.equal.presentation.sign.viewmodel.PetLivingViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class PetLivingViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<PetLiving>(
        viewGroup,
        R.layout.item_sign_pet_living
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSignPetLivingBinding =
        ItemSignPetLivingBinding.bind(itemView)

    val viewmodel: PetLivingViewModel = PetLivingViewModel()

    init {
        viewmodel.notify {
            item.error = false
            item.errorMessage = getString(R.string.sign_comment_125)

            if (item.mainActPlaceCode == it.toInt()) {
                item.mainActPlaceCode = -1
            } else {
                item.mainActPlaceCode = it.toInt()
            }
            viewmodel.model = item
            binding.model = viewmodel
            sendEvent(hashCode())
        }

        viewmodel.notifyAsk {
            item.petAsk = it
            viewmodel.model = item
            binding.model = viewmodel
        }

        viewmodel.notifyEtc {
            item.error = false
            item.mainActPlaceCode = 4
            item.mainActPlaceEtc = it
            viewmodel.model = item
            binding.model = viewmodel
            sendEvent(hashCode())
        }

    }


    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: PetLiving?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }

}
package pet.perpet.equal.presentation.sign.viewholder

import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSignPetSubmitBinding
import pet.perpet.equal.presentation.sign.viewmodel.PetSubmitViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class PetSubmitViewHolder  (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<Any>(
        viewGroup,
        R.layout.item_sign_pet_submit
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSignPetSubmitBinding =
        ItemSignPetSubmitBinding.bind(itemView)

    val viewmodel:  PetSubmitViewModel = PetSubmitViewModel()

    init {
        viewmodel.notify {
            sendEvent(hashCode())
        }
    }


    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: Any?) {
        super.onRefresh(t)
        binding.model = viewmodel
    }

}
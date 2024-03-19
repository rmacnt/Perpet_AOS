package pet.perpet.equal.presentation.home.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.pet.Pet
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemMainPetSelectBinding
import pet.perpet.equal.presentation.home.viewmodel.MainItemViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class MainItemViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<Pet>(viewGroup, R.layout.item_main_pet_select) {

    //======================================================================
    // Variables
    //======================================================================

    val viewmodel: MainItemViewModel = object : MainItemViewModel() {

    }

    val binding: ItemMainPetSelectBinding = ItemMainPetSelectBinding.bind(itemView)

    init {
        viewmodel.context = {
            context
        }

        viewmodel.notify {
            sendEvent(hashCode())
        }
    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: Pet?) {
        viewmodel.model = t
        binding.model = viewmodel
    }
}
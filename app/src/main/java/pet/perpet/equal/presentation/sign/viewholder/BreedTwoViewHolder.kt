package pet.perpet.equal.presentation.sign.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.profile.Breed
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemFlexboxBinding
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class BreedTwoViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<Breed>(viewGroup, R.layout.item_flexbox) {

    //======================================================================
    // Variables
    //======================================================================

    val viewmodel: BreedBoxTwoViewModel = object : BreedBoxTwoViewModel() {

    }

    val binding: ItemFlexboxBinding = ItemFlexboxBinding.bind(itemView)

    init {
        viewmodel.notify {
            viewmodel.model = it
            sendEvent(hashCode())
        }
    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: Breed?) {
        viewmodel.model = t
        binding.model = viewmodel
    }
}
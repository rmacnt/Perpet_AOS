package pet.perpet.equal.presentation.sign.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.profile.Breed
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSearchBinding
import pet.perpet.equal.presentation.sign.viewmodel.BreedBoxViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class BreedSearchViewHolder(viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<Breed>(viewGroup, R.layout.item_search) {

    //======================================================================
    // Variables
    //======================================================================

    val viewmodel: BreedBoxViewModel = BreedBoxViewModel()

    val binding: ItemSearchBinding = ItemSearchBinding.bind(itemView)

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
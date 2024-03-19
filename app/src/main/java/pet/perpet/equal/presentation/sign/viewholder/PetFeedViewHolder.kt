package pet.perpet.equal.presentation.sign.viewholder

import android.view.ViewGroup
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSignPetFeedBinding
import pet.perpet.equal.presentation.sign.model.PetFeed
import pet.perpet.equal.presentation.sign.viewmodel.PetFeedViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder


class PetFeedViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<PetFeed>(
        viewGroup,
        R.layout.item_sign_pet_feed
    ) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemSignPetFeedBinding =
        ItemSignPetFeedBinding.bind(itemView)

    val viewmodel: PetFeedViewModel = PetFeedViewModel()

    init {

        viewmodel.notifyAsk {
            item.petAsk = it
            viewmodel.model = item
            binding.model = viewmodel
        }

        viewmodel.notify {
            item.error = false
            if (item.feedAmountCode == it.toInt()) {
                item.feedAmountCode = -1
            } else {
                item.feedAmountCode = it.toInt()
            }
            viewmodel.model = item
            binding.model = viewmodel
            sendEvent(hashCode())
        }
    }


    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: PetFeed?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }

}
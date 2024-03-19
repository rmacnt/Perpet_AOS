package pet.perpet.equal.presentation.subscribe.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.card.CardList
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemSubscribeCardBinding
import pet.perpet.equal.presentation.subscribe.viewmodel.SearchCardItemVIewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class SearchCardItemViewHolder  (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<CardList>(viewGroup, R.layout.item_subscribe_card) {

    //======================================================================
    // Variables
    //======================================================================

    val viewmodel: SearchCardItemVIewModel = object : SearchCardItemVIewModel() {
    }

    val binding: ItemSubscribeCardBinding = ItemSubscribeCardBinding.bind(itemView)

    init {
        viewmodel.notify {
            viewmodel.model = it
            sendEvent(hashCode())
        }

    }
    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: CardList?) {
        viewmodel.model = t
        binding.model = viewmodel
    }
}
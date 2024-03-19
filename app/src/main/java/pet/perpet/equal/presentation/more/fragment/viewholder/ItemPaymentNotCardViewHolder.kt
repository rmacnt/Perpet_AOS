package pet.perpet.equal.presentation.more.fragment.viewholder

import android.view.ViewGroup
import okhttp3.internal.notify
import pet.perpet.domain.model.card.CardList
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemCardInfoNotBinding
import pet.perpet.equal.presentation.more.viewmodel.payment.ItemPaymentNotCardViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class ItemPaymentNotCardViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<CardList>(viewGroup, R.layout.item_card_info_not) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemCardInfoNotBinding = ItemCardInfoNotBinding.bind(itemView)

    val viewmodel: ItemPaymentNotCardViewModel = ItemPaymentNotCardViewModel()

    init {

        viewmodel.notify {
            sendEvent(hashCode())
        }
    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: CardList?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }
}
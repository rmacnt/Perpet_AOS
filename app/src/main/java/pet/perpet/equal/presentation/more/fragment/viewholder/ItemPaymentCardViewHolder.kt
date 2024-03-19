package pet.perpet.equal.presentation.more.fragment.viewholder

import android.view.ViewGroup
import pet.perpet.domain.model.card.CardList
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ItemCardInfoBinding
import pet.perpet.equal.presentation.more.viewmodel.payment.ItemPaymentCardViewModel
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder

class ItemPaymentCardViewHolder (viewGroup: ViewGroup?) :
    BaseRecyclerViewHolder<CardList>(viewGroup, R.layout.item_card_info) {

    //======================================================================
    // Variables
    //======================================================================

    val binding: ItemCardInfoBinding = ItemCardInfoBinding.bind(itemView)

    val viewmodel: ItemPaymentCardViewModel = ItemPaymentCardViewModel()

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onRefresh(t: CardList?) {
        super.onRefresh(t)
        viewmodel.model = t
        binding.model = viewmodel
    }
}
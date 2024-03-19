package pet.perpet.equal.presentation.more.viewmodel.payment

import android.view.View
import pet.perpet.domain.model.card.CardList
import pet.perpet.equal.support.navigation.showCardOrAddressOrderInfo

class ItemPaymentCardViewModel  (var model: CardList? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val cardName: String?
        get() = "${model?.card_name.orEmpty()} / ${model?.card_number.orEmpty()}"

    val cardInsertDate: String?
        get() = "등록일: ${model?.toDiff()}"


    //======================================================================
    // Public Methods
    //======================================================================

    fun onListClick(view: View) {
        view.context.showCardOrAddressOrderInfo("address")
    }
}
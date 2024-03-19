package pet.perpet.equal.presentation.more.viewmodel.shipping

import android.text.Spanned
import android.util.Log
import android.view.View
import pet.perpet.data.nonnull
import pet.perpet.domain.model.address.ListAddress
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getString
import pet.perpet.equal.presentation.goShippingEdit
import pet.perpet.equal.support.navigation.showCardOrAddressOrderInfo
import pet.perpet.framework.util.http.HtmlFactory

class ItemMoreAddressViewModel   (var model: ListAddress? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val name: String?
        get() = "${model?.recipient.orEmpty()} / ${model?.phone.orEmpty()}"

    val address: String?
        get() = "${model?.address} ${model?.address_detail}"


    val orderStatus : Boolean?
        get() = model?.ordersInfo?.size.nonnull() > 0

    val list: Spanned?
        get() = getString(R.string.payment_comment25).orEmpty().run { HtmlFactory.fromHtml(this) }


    //======================================================================
    // Public Methods
    //======================================================================

    fun onListClick(view: View) {
        view.context.showCardOrAddressOrderInfo("address")
    }

    fun onEditClick(view: View) {
        view.context?.let {
            model?.let {item->
                it.goShippingEdit(item)
            }
        }
    }
}
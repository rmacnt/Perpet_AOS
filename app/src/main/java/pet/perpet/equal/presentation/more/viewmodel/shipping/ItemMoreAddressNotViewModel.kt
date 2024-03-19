package pet.perpet.equal.presentation.more.viewmodel.shipping

import android.text.Spanned
import android.view.View
import pet.perpet.data.nonnull
import pet.perpet.domain.model.address.ListAddress
import pet.perpet.equal.R
import pet.perpet.equal.presentation.base.ui.AppAlertDialog
import pet.perpet.equal.presentation.getString
import pet.perpet.equal.presentation.goShippingEdit
import pet.perpet.framework.util.http.HtmlFactory

class ItemMoreAddressNotViewModel (var model: ListAddress? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val name: String?
        get() = "${model?.recipient.orEmpty()} / ${model?.phone.orEmpty()}"

    val address: String?
        get() = "${model?.address} ${model?.address_detail}"

    val delete: Spanned?
        get() = getString(R.string.payment_comment26).orEmpty().run { HtmlFactory.fromHtml(this) }


    private var notify: ((value: ListAddress) -> Unit)? = null

    val serviceType: Boolean?
        get() = model?.address?.contains("서울").nonnull() || model?.address?.contains("경기").nonnull() || model?.address?.contains("인천").nonnull()

    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: ((value: ListAddress) -> Unit)?) {
        notify = value
    }

    fun onDeleteClick(view: View) {
        AppAlertDialog(
            view.context,
            getString(R.string.dialog_title),
            getString(R.string.more_comment22),
            getString(R.string.more_comment24),
            getString(R.string.no)

        ).show(
            onClickNegative = {
                it.dismiss()
            },
            onClickPositive = {
                notify?.let {call ->
                    model?.let {
                        call(it)
                    }
                }
                it.dismiss()
            }

        )


    }

    fun onEditClick(view: View) {
        view.context?.let {
            model?.let {item->
                it.goShippingEdit(item)
            }
        }
    }
}
package pet.perpet.equal.presentation.more.viewmodel.payment

import android.text.Spanned
import android.view.View
import pet.perpet.domain.model.card.CardList
import pet.perpet.equal.R
import pet.perpet.equal.presentation.base.ui.AppAlertDialog
import pet.perpet.equal.presentation.getString
import pet.perpet.framework.util.http.HtmlFactory

class ItemPaymentNotCardViewModel  (var model: CardList? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val cardName: String?
        get() = "${model?.card_name.orEmpty()} / ${model?.card_number.orEmpty()}"

    val cardInsertDate: String?
        get() = "등록일: ${model?.toDiff()}"

    val delete: Spanned?
        get() = getString(R.string.payment_comment26).orEmpty().run { HtmlFactory.fromHtml(this) }

    private var notify: ((value: CardList) -> Unit)? = null

    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: ((value: CardList) -> Unit)?) {
        notify = value
    }

    fun onDeleteClick(view: View) {
        AppAlertDialog(
            view.context,
            getString(R.string.dialog_title),
            getString(R.string.more_comment23),
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
}
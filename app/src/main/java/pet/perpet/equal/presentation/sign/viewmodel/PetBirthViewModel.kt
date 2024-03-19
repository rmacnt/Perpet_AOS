package pet.perpet.equal.presentation.sign.viewmodel

import android.text.Spanned
import android.view.View
import pet.perpet.data.nonnull
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getString
import pet.perpet.equal.presentation.sign.model.PetBirth
import pet.perpet.framework.util.http.HtmlFactory

class PetBirthViewModel(var model: PetBirth? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val title: String?
        get() = getString(R.string.sign_comment_13)?.let { String.format(it, model?.petName.nonnull()) }


    val petBirthAsk: Boolean?
        get() = model?.petAsk

    val viewAsk: Spanned?
        get() = getString(R.string.sign_comment_7).orEmpty().run { HtmlFactory.fromHtml(this) }

    val birth : String?
        get() = if(model?.petBirth.isNullOrEmpty().not()) {
            model?.petBirth.nonnull()
        } else {
          ""
        }

    private var notifyAsk: ((check: Boolean) -> Unit)? = null

    private var notifyDialog: ((check: Boolean) -> Unit)? = null


    //======================================================================
    // Public Methods
    //======================================================================

    fun notifyAsk(value: ((check: Boolean) -> Unit)?) {
        notifyAsk = value
    }

    fun notifyDialog(value: ((check: Boolean) -> Unit)?) {
        notifyDialog = value
    }

    fun onBirthAskClick(view: View?) {
        notifyAsk?.let {call ->
            model?.petAsk?.let {
                call(it.not())
            }
        }
    }

    fun onBirthClick(view: View?) {
        notifyDialog?.let { call ->
            call(true)
        }
    }
}
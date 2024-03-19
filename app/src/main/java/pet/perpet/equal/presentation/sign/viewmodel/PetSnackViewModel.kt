package pet.perpet.equal.presentation.sign.viewmodel

import android.text.Spanned
import android.view.View
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getString
import pet.perpet.equal.presentation.sign.model.PetSnack
import pet.perpet.framework.util.http.HtmlFactory

class PetSnackViewModel (var model: PetSnack? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val petSnack: Int?
        get() = model?.snack

    val viewAsk: Spanned?
        get() = getString(R.string.sign_comment_7).orEmpty().run { HtmlFactory.fromHtml(this) }

    val petSnackAsk: Boolean?
        get() = model?.petAsk

    private var notify: ((value: String) -> Unit)? = null

    private var notifyAsk: ((check: Boolean) -> Unit)? = null


    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: ((value: String) -> Unit)?) {
        notify = value
    }

    fun notifyAsk(value: ((check: Boolean) -> Unit)?) {
        notifyAsk = value
    }

    fun onSnackAskClick(view: View?) {
        notifyAsk?.let {call ->
            model?.petAsk?.let {
                call(it.not())
            }
        }
    }

    fun onSnackClick(view: View?) {
        notify?.let {call ->
            call(view?.tag.toString())
        }
    }
}

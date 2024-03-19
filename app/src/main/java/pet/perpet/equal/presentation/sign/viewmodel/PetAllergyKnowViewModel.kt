package pet.perpet.equal.presentation.sign.viewmodel

import android.text.Spanned
import android.view.View
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getString
import pet.perpet.equal.presentation.sign.model.PetAllergyKnow
import pet.perpet.equal.presentation.sign.model.PetSnack
import pet.perpet.framework.util.http.HtmlFactory

class PetAllergyKnowViewModel (var model: PetAllergyKnow? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val petKnow: Int?
        get() = model?.howToKnowAllergyCode

    val petVisibleOne: Boolean
        get() = when (petKnow) {
            0, -1 -> true
            else -> false
        }

    val petVisibleTwo: Boolean
        get() = when (petKnow) {
            1, -1 -> true
            else -> false
        }

    val viewAsk: Spanned?
        get() = getString(R.string.sign_comment_7).orEmpty().run { HtmlFactory.fromHtml(this) }

    val error: Boolean?
        get() = model?.error

    val petKnowAsk: Boolean?
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

    fun onKnowAskClick(view: View?) {
        notifyAsk?.let {call ->
            model?.petAsk?.let {
                call(it.not())
            }
        }
    }


    fun onKnowClick(view: View?) {
        notify?.let {call ->
            call(view?.tag.toString())
        }
    }
}

package pet.perpet.equal.presentation.sign.viewmodel

import android.text.Spanned
import android.view.View
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getString
import pet.perpet.equal.presentation.sign.model.PetLivingTogether
import pet.perpet.framework.util.http.HtmlFactory

class PetLivingTogetherViewModel (var model: PetLivingTogether? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val petRelationshipCode: Int?
        get() = model?.relationshipCode

    val petVisibleOne: Boolean
        get() = when (petRelationshipCode) {
            0, -1 -> true
            else -> false
        }

    val petVisibleTwo: Boolean
        get() = when (petRelationshipCode) {
            1, -1 -> true
            else -> false
        }

    val petVisibleThree: Boolean
        get() = when (petRelationshipCode) {
            2, -1 -> true
            else -> false
        }

    val error: Boolean?
        get() = model?.error

    val viewAsk: Spanned?
        get() = getString(R.string.sign_comment_7).orEmpty().run { HtmlFactory.fromHtml(this) }

    val petLivingAsk: Boolean?
        get() = model?.petAsk


    private var notify: ((value: String) -> Unit)? = null

    private var notifyAsk: ((check: Boolean) -> Unit)? = null


    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: ((value: String) -> Unit)?) {
        notify = value
    }

    fun notifyAsk(value: ((value: Boolean) -> Unit)?) {
        notifyAsk = value
    }


    fun onRelationshipClick(view: View?) {
        notify?.let {call ->
            call(view?.tag.toString())
        }
    }

    fun onLivingAskClick(view: View?) {
        notifyAsk?.let {call ->
            model?.petAsk?.let {
                call(it.not())
            }
        }
    }

}

package pet.perpet.equal.presentation.sign.viewmodel

import android.text.Spanned
import android.view.View
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getString
import pet.perpet.equal.presentation.sign.model.PetEnergy
import pet.perpet.framework.util.http.HtmlFactory

class PetEnergyViewModel (var model: PetEnergy? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val petConditionsCode: Int?
        get() = model?.conditionsCode

    val petVisibleOne: Boolean
        get() = when (petConditionsCode) {
            0, -1 -> true
            else -> false
        }

    val petVisibleTwo: Boolean
        get() = when (petConditionsCode) {
            1, -1 -> true
            else -> false
        }

    val petVisibleThree: Boolean
        get() = when (petConditionsCode) {
            2, -1 -> true
            else -> false
        }

    val error: Boolean?
        get() = model?.error

    val petEnergyAsk: Boolean?
        get() = model?.petAsk

    val viewAsk: Spanned?
        get() = getString(R.string.sign_comment_7).orEmpty().run { HtmlFactory.fromHtml(this) }


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

    fun onEnergyAskClick(view: View?) {
        notifyAsk?.let {call ->
            model?.petAsk?.let {
                call(it.not())
            }
        }
    }


    fun onConditionClick(view: View?) {
        notify?.let {call ->
            call(view?.tag.toString())
        }
    }
}

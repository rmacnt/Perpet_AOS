package pet.perpet.equal.presentation.sign.viewmodel

import android.text.Spanned
import android.view.View
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getString
import pet.perpet.equal.presentation.sign.model.PetWalk
import pet.perpet.framework.util.http.HtmlFactory

class PetWalkViewModel  (var model: PetWalk? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val petWalkCode: Int?
        get() = model?.walkCode

    val petVisibleOne: Boolean
        get() = when (petWalkCode) {
            0, -1 -> true
            else -> false
        }

    val petVisibleTwo: Boolean
        get() = when (petWalkCode) {
            1, -1 -> true
            else -> false
        }

    val petVisibleThree: Boolean
        get() = when (petWalkCode) {
            2, -1 -> true
            else -> false
        }

    val petVisibleFour: Boolean
        get() = when (petWalkCode) {
            3, -1 -> true
            else -> false
        }

    val error: Boolean?
        get() = model?.error

    val viewAsk: Spanned?
        get() = getString(R.string.sign_comment_7).orEmpty().run { HtmlFactory.fromHtml(this) }

    val petWalkAsk: Boolean?
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

    fun onWalkAskClick(view: View?) {
        notifyAsk?.let {call ->
            model?.petAsk?.let {
                call(it.not())
            }
        }
    }


    fun onWalkClick(view: View?) {
        notify?.let {call ->
            call(view?.tag.toString())
        }
    }
}

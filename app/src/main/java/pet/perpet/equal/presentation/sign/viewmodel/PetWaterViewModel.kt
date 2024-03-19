package pet.perpet.equal.presentation.sign.viewmodel

import android.text.Spanned
import android.view.View
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getString
import pet.perpet.equal.presentation.sign.model.PetWater
import pet.perpet.framework.util.http.HtmlFactory

class PetWaterViewModel (var model: PetWater? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val petDrinkingAmountCode: Int?
        get() = model?.drinkingAmountCode

    val petVisibleOne: Boolean
        get() = when (petDrinkingAmountCode) {
            0, -1 -> true
            else -> false
        }

    val petVisibleTwo: Boolean
        get() = when (petDrinkingAmountCode) {
            1, -1 -> true
            else -> false
        }

    val petVisibleThree: Boolean
        get() = when (petDrinkingAmountCode) {
            2, -1 -> true
            else -> false
        }

    val petVisibleFour: Boolean
        get() = when (petDrinkingAmountCode) {
            3, -1 -> true
            else -> false
        }

    val error: Boolean?
        get() = model?.error

    val viewAsk: Spanned?
        get() = getString(R.string.sign_comment_7).orEmpty().run { HtmlFactory.fromHtml(this) }

    val petWaterAsk: Boolean?
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

    fun onWaterAskClick(view: View?) {
        notifyAsk?.let {call ->
            model?.petAsk?.let {
                call(it.not())
            }
        }
    }



    fun onWaterClick(view: View?) {
        notify?.let {call ->
            call(view?.tag.toString())
        }
    }
}

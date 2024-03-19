package pet.perpet.equal.presentation.sign.viewmodel

import android.text.Spanned
import android.view.View
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getString
import pet.perpet.equal.presentation.sign.model.PetLiving
import pet.perpet.framework.util.http.HtmlFactory

class PetLivingViewModel (var model: PetLiving? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val petLivingAsk: Boolean?
        get() = model?.petAsk

    val viewAsk: Spanned?
        get() = getString(R.string.sign_comment_7).orEmpty().run { HtmlFactory.fromHtml(this) }


    val error: Boolean?
        get() = model?.error

    val errorMessage: String?
        get() = model?.errorMessage

    val petMainActPlaceCode: Int?
        get() = model?.mainActPlaceCode

    val petVisibleOne: Boolean
        get() = when (petMainActPlaceCode) {
            0, -1 -> true
            else -> false
        }

    val petVisibleTwo: Boolean
        get() = when (petMainActPlaceCode) {
            1, -1 -> true
            else -> false
        }

    val petVisibleThree: Boolean
        get() = when (petMainActPlaceCode) {
            2, -1 -> true
            else -> false
        }

    val petVisibleFour: Boolean
        get() = when (petMainActPlaceCode) {
            3, -1 -> true
            else -> false
        }

    val petVisibleFive: Boolean
        get() = when (petMainActPlaceCode) {
            4, -1 -> true
            else -> false
        }

    private var notify: ((value: String) -> Unit)? = null

    private var notifyAsk: ((check: Boolean) -> Unit)? = null

    private var notifyEtc: ((value: String) -> Unit)? = null

    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: ((value: String) -> Unit)?) {
        notify = value
    }

    fun notifyEtc(value: ((value: String) -> Unit)?) {
        notifyEtc = value
    }


    fun notifyAsk(value: ((check: Boolean) -> Unit)?) {
        notifyAsk = value
    }

    fun onLivingAskClick(view: View?) {
        notifyAsk?.let {call ->
            model?.petAsk?.let {
                call(it.not())
            }
        }
    }

    fun onLivingClick(view: View?) {
        notify?.let {call ->
            call(view?.tag.toString())
        }
    }

    fun onLivingTextChange(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {

        notifyEtc?.let {call->
            call(text.toString())
        }
    }
}

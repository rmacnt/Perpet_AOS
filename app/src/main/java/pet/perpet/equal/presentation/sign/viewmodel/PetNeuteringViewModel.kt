package pet.perpet.equal.presentation.sign.viewmodel

import android.text.Spanned
import android.view.View
import pet.perpet.data.nonnull
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getString
import pet.perpet.equal.presentation.sign.model.PetNeutering
import pet.perpet.equal.presentation.sign.model.PetSelect
import pet.perpet.framework.util.http.HtmlFactory

class PetNeuteringViewModel(var model: PetNeutering? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val petNeutralization: Int
        get() = model?.neutralizationCode.nonnull()

    val petNeutralizationAsk: Boolean?
        get() = model?.petAsk

    val viewAsk: Spanned?
        get() = getString(R.string.sign_comment_7).orEmpty().run { HtmlFactory.fromHtml(this) }


    val error: Boolean?
        get() = model?.error

    val petVisibleOne: Boolean
        get() = when (petNeutralization) {
            0, -1 -> true
            else -> false
        }

    val petVisibleTwo: Boolean
        get() = when (petNeutralization) {
            1, -1 -> true
            else -> false
        }

    val petVisibleThree: Boolean
        get() = when (petNeutralization) {
            2, -1 -> true
            else -> false
        }
    private var notify: ((value: Int) -> Unit)? = null

    private var notifyAsk: ((check: Boolean) -> Unit)? = null

    //======================================================================
    // Public Methods
    //======================================================================


    fun notify(value: ((value: Int) -> Unit)?) {
        notify = value
    }

    fun notifyAsk(value: ((check: Boolean) -> Unit)?) {
        notifyAsk = value
    }

    fun onTNeutralizationAskClick(view: View?) {
        notifyAsk?.let {call ->
            model?.petAsk?.let {
                call(it.not())
            }
        }
    }


    fun onNeuteringClick(view: View?) {
        notify?.let { call ->
            call(view?.tag.toString().toInt())
        }
    }


}
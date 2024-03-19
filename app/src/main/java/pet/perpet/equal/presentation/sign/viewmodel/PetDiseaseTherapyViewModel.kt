package pet.perpet.equal.presentation.sign.viewmodel

import android.text.Spanned
import android.view.View
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getString
import pet.perpet.equal.presentation.sign.model.PetDiseaseTherapy
import pet.perpet.framework.util.http.HtmlFactory

class PetDiseaseTherapyViewModel  (var model: PetDiseaseTherapy? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val petDiseaseTherapy: Int?
        get() = model?.diseaseTreatCode

    val petVisibleOne: Boolean
        get() = when (petDiseaseTherapy) {
            0, -1 -> true
            else -> false
        }

    val error: Boolean?
        get() = model?.error

    val viewAsk: Spanned?
        get() = getString(R.string.sign_comment_7).orEmpty().run { HtmlFactory.fromHtml(this) }

    val petDiseaseAsk: Boolean?
        get() = model?.petAsk

    val petVisibleTwo: Boolean
        get() = when (petDiseaseTherapy) {
            1, -1 -> true
            else -> false
        }

    private var notifyAsk: ((check: Boolean) -> Unit)? = null

    private var notify: ((value: String) -> Unit)? = null


    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: ((value: String) -> Unit)?) {
        notify = value
    }

    fun notifyAsk(value: ((value: Boolean) -> Unit)?) {
        notifyAsk = value
    }

    fun onDiseaseTherapyAskClick(view: View?) {
        notifyAsk?.let {call ->
            model?.petAsk?.let {
                call(it.not())
            }
        }
    }

    fun onDiseaseTherapyClick(view: View?) {
        notify?.let {call ->
            call(view?.tag.toString())
        }
    }
}

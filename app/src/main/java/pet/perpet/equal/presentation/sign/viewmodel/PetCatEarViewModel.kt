package pet.perpet.equal.presentation.sign.viewmodel

import android.text.Spanned
import android.view.View
import pet.perpet.data.nonnull
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getString
import pet.perpet.equal.presentation.sign.model.PetCatEar

class PetCatEarViewModel (var model: PetCatEar? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val title: String?
        get() = getString(R.string.sign_comment_62)?.let { String.format(it, model?.petName.nonnull()) }

    val ear: Int
        get() = when (model?.petEar) {
            "Y" -> 0
            "N" -> 1
            else -> -1
        }

    val error: Boolean?
        get() = model?.error

    private var notify: ((value: String) -> Unit)? = null


    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: ((value: String) -> Unit)?) {
        notify = value
    }

    fun onEarClick(view: View?) {
        notify?.let {call->
            call(when (view?.tag.toString()) {
                "0" -> {
                    "Y"
                }
                "1" -> {
                    "N"
                }
                else -> "Y"
            })
        }
    }
}
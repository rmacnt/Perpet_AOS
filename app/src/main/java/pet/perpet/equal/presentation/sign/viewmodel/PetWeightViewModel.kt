package pet.perpet.equal.presentation.sign.viewmodel

import android.text.Spanned
import android.util.Log
import android.view.View
import pet.perpet.data.nonnull
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getString
import pet.perpet.equal.presentation.sign.model.PetWeight
import pet.perpet.framework.util.http.HtmlFactory

class PetWeightViewModel(var model: PetWeight? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val title: String?
        get() = getString(R.string.sign_comment_16)?.let { String.format(it, model?.petName.nonnull()) }

    val petWeightAsk: Boolean?
        get() = model?.petAsk

    val error: Boolean?
        get() = model?.error

    val weight: String?
        get() = model?.petWeight

    val viewAsk: Spanned?
        get() = getString(R.string.sign_comment_7).orEmpty().run { HtmlFactory.fromHtml(this) }


    private var notify: ((value: String) -> Unit)? = null

    private var notifyAsk: ((check: Boolean) -> Unit)? = null

    private var notifyDialog: ((check: Boolean) -> Unit)? = null


    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: ((value: String) -> Unit)?) {
        notify = value
    }

    fun notifyAsk(value: ((check: Boolean) -> Unit)?) {
        notifyAsk = value
    }



    fun onWeightAskClick(view: View?) {
        notifyAsk?.let {call ->
            model?.petAsk?.let {
                call(it.not())
            }
        }
    }

    fun onWeightTextChange(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        if (text.toString().contains(".").nonnull().not()) {
            notify?.let { call ->
                call(text.toString())
            }
        } else {
            if(text.toString()[text.toString().length-1].toString() == ".") {
                notify?.let { call ->
                    call(text.toString())
                }
            } else {
                notify?.let { call ->
                    val message = text.toString().substring(0, text.toString().length-1)
                    if(message[message.length-1].toString() ==".") {
                        call(text.toString().substring(0, text.toString().length))
                    } else {
                        call(text.toString().substring(0, text.toString().length-1))
                    }
                }
            }
        }

    }
}
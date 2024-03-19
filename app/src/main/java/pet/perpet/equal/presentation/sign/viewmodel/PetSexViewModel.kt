package pet.perpet.equal.presentation.sign.viewmodel

import android.text.Spanned
import android.view.View
import pet.perpet.data.nonnull
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getString
import pet.perpet.equal.presentation.sign.model.PetSex
import pet.perpet.framework.util.http.HtmlFactory

class PetSexViewModel (var model: PetSex? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val title: String?
        get() = getString(R.string.sign_comment_6)?.let { String.format(it, model?.petName.nonnull()) }

    val petSex: Int
        get() = when (model?.petSex) {
            "M" -> 0
            "F" -> 1
            else -> -1
        }

    val petSexAsk: Boolean?
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

    fun onSexAskClick(view: View?) {
        notifyAsk?.let {call ->
            model?.petAsk?.let {
                call(it.not())
            }
        }
    }

    fun onSexClick(view: View?) {
        notify?.let {call->
            call(when (view?.tag.toString()) {
                "0" -> {
                    "M"
                }
                "1" -> {
                    "F"
                }
                else -> ""
            })
        }
    }

}
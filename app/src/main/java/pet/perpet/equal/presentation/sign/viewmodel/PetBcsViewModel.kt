package pet.perpet.equal.presentation.sign.viewmodel

import android.text.Spanned
import android.view.View
import pet.perpet.data.nonnull
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getString
import pet.perpet.equal.presentation.sign.model.PetBcs
import pet.perpet.framework.util.http.HtmlFactory

class PetBcsViewModel (var model: PetBcs? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val title: String?
        get() = getString(R.string.sign_comment_18)?.let { String.format(it, model?.petName.nonnull()) }


    val change: Spanned?
        get() = getString(R.string.sign_comment_76).orEmpty().run { HtmlFactory.fromHtml(this) }


    val petBcsAsk: Boolean?
        get() = model?.petAsk

    val petSelect: Int?
        get() = when(model?.petSelect) {
            "dog"-> 1
            "cat"-> 2
            else -> -1
        }

    val error: Boolean?
        get() = model?.error

    val petBcs: Int?
        get() = model?.petBcsCode.nonnull()

    val petBcsAllView: Boolean
        get() = model?.petBcsAllView.nonnull()

    val viewAsk: Spanned?
        get() = getString(R.string.sign_comment_7).orEmpty().run { HtmlFactory.fromHtml(this) }

    private var notify: ((value: Int) -> Unit)? = null

    private var notifyAsk: ((check: Boolean) -> Unit)? = null

    private var notifyChange: ((check: Boolean) -> Unit)? = null

    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: ((value: Int) -> Unit)?) {
        notify = value
    }

    fun notifyAsk(value: ((check: Boolean) -> Unit)?) {
        notifyAsk = value
    }
    fun notifyChange(value: ((check: Boolean) -> Unit)?) {
        notifyChange = value
    }


    fun onBcsAskClick(view: View?) {
        notifyAsk?.let {call ->
            model?.petAsk?.let {
                call(it.not())
            }
        }
    }

    fun onBcsClick(view: View?) {
        notify?.let {call->
            call(view?.tag.toString().toInt())
        }
    }

    fun onChangeClick(view: View?) {
        notifyChange?.let {call ->
            model?.petBcsAllView?.let {
                call(it.not())
            }
        }
    }




}
package pet.perpet.equal.presentation.sign.viewmodel

import android.text.Spanned
import android.view.View
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getString
import pet.perpet.equal.presentation.sign.model.PetAppetite
import pet.perpet.framework.util.http.HtmlFactory

class PetAppetiteViewModel (var model: PetAppetite? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val petAppetiteChangeCode: Int?
        get() = model?.appetiteChangeCode

    val petVisibleOne: Boolean
        get() = when (petAppetiteChangeCode) {
            0, -1 -> true
            else -> false
        }

    val petVisibleTwo: Boolean
        get() = when (petAppetiteChangeCode) {
            1, -1 -> true
            else -> false
        }

    val petVisibleThree: Boolean
        get() = when (petAppetiteChangeCode) {
            2, -1 -> true
            else -> false
        }

    val error: Boolean?
        get() = model?.error

    val viewAsk: Spanned?
        get() = getString(R.string.sign_comment_7).orEmpty().run { HtmlFactory.fromHtml(this) }

    val petAppetiteAsk: Boolean?
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

    fun onBcsAskClick(view: View?) {
        notifyAsk?.let {call ->
            model?.petAsk?.let {
                call(it.not())
            }
        }
    }


    fun onAppetiteClick(view: View?) {
        notify?.let {call ->
            call(view?.tag.toString())
        }
    }
}

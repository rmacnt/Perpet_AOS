package pet.perpet.equal.presentation.sign.viewmodel

import android.text.Spanned
import android.view.View
import pet.perpet.data.nonnull
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getString
import pet.perpet.equal.presentation.sign.model.PetDiseaseTherapy
import pet.perpet.equal.presentation.sign.model.PetFeed
import pet.perpet.framework.util.http.HtmlFactory

class PetFeedViewModel (var model: PetFeed? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val title: String?
        get() = getString(R.string.sign_comment_67)?.let { String.format(it, model?.petName.nonnull()) }

    val petFeedAmountCode: Int?
        get() = model?.feedAmountCode

    val petVisibleOne: Boolean
        get() = when (petFeedAmountCode) {
            0, -1 -> true
            else -> false
        }

    val petVisibleTwo: Boolean
        get() = when (petFeedAmountCode) {
            1, -1 -> true
            else -> false
        }

    val petVisibleThree: Boolean
        get() = when (petFeedAmountCode) {
            2, -1 -> true
            else -> false
        }

    val petVisibleFour: Boolean
        get() = when (petFeedAmountCode) {
            3, -1 -> true
            else -> false
        }

    val petVisibleFive: Boolean
        get() = when (petFeedAmountCode) {
            4, -1 -> true
            else -> false
        }

    val petVisibleSix: Boolean
        get() = when (petFeedAmountCode) {
            5, -1 -> true
            else -> false
        }

    val error: Boolean?
        get() = model?.error

    val viewAsk: Spanned?
        get() = getString(R.string.sign_comment_7).orEmpty().run { HtmlFactory.fromHtml(this) }

    val petFeedAsk: Boolean?
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

    fun onFeedAskClick(view: View?) {
        notifyAsk?.let {call ->
            model?.petAsk?.let {
                call(it.not())
            }
        }
    }

    fun onFeedClick(view: View?) {
        notify?.let {call ->
            call(view?.tag.toString())
        }
    }
}

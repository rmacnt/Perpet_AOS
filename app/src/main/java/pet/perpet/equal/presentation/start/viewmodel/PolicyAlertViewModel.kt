package pet.perpet.equal.presentation.start.viewmodel

import android.os.Bundle
import android.text.Spanned
import android.view.View
import androidx.fragment.app.Fragment
import pet.perpet.equal.R
import pet.perpet.equal.presentation.dispatchDismissToResult
import pet.perpet.equal.presentation.getColor
import pet.perpet.equal.presentation.start.model.Policy
import pet.perpet.equal.support.navigation.showPolicyInfo
import pet.perpet.framework.fragment.BottomSheetDialogFragment
import pet.perpet.framework.fragment.DialogFragment
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.util.http.HtmlFactory

class PolicyAlertViewModel(fragment: Fragment) : UseViewModel(fragment) {

    var allClick: Boolean = false

    var checkUse: Boolean = false
    val viewComment: Spanned?
        get() = getString(R.string.bottom_comment_7).orEmpty().run { HtmlFactory.fromHtml(this) }


    var termUse: Boolean = false

    var infoUse: Boolean = false

    var marketingUse: Boolean = false

    val onTermTextColor: Int?
        get() = getColor(
            if (termUse.not()) {
                R.color.text_hint_color
            } else {
                R.color.black
            }
        )

    val onInfoTextColor: Int?
        get() = getColor(
            if (infoUse.not()) {
                R.color.text_hint_color
            } else {
                R.color.black
            }
        )

    val onMarketingTextColor: Int?
        get() = getColor(
            if (marketingUse.not()) {
                R.color.text_hint_color
            } else {
                R.color.black
            }
        )

    //======================================================================
    // Public Methods
    //======================================================================

    fun onAllClick(view: View) {
        if (allClick.not()) {
            termUse = true
            infoUse = true
            marketingUse = true
            allClick = true
            checkUse = true

        } else {
            termUse = false
            infoUse = false
            marketingUse = false
            allClick = false
            checkUse = false
        }

        executeViewBinding()
    }

    fun onTermClick(view: View) {
        activity?.showPolicyInfo(
            getString(R.string.bottom_comment_11),
            getString(R.string.term_url)
        )
    }

    fun onTermItemClick(view: View) {
        termUse = termUse.not()
        allClick = termUse && infoUse && marketingUse
        checkUse = termUse && infoUse
        executeViewBinding()
    }

    fun onInfoClick(view: View) {
        activity?.showPolicyInfo(
            getString(R.string.bottom_comment_10),
            getString(R.string.info_url)
        )
    }

    fun onInfoItemClick(view: View) {
        infoUse = infoUse.not()
        allClick = termUse && infoUse && marketingUse
        checkUse = termUse && infoUse
        executeViewBinding()
    }

    fun onMarketingClick(view: View) {
        activity?.showPolicyInfo(
            getString(R.string.bottom_comment_12),
            getString(R.string.maketing_url)
        )
    }

    fun onMarketingItemClick(view: View) {
        marketingUse = marketingUse.not()
        allClick = termUse && infoUse && marketingUse
        executeViewBinding()
    }

    fun onClick(view: View) {
        val b = Bundle()
        val term = if (termUse) "Y" else "N"
        val info = if (infoUse) "Y" else "N"
        val marketing = if (marketingUse) "Y" else "N"
        b.putString("term", term)
        b.putString("info", info)
        b.putString("marketing", marketing)
        val policy = Policy(term , info , marketing)
        fragment?.dispatchDismissToResult(policy)
    }

    fun onNegativeClick(view: View) {
        if (fragment is BottomSheetDialogFragment<*>) {
            Bundle().apply {
                this.putBoolean("success", false)
            }.run {
                (fragment as BottomSheetDialogFragment<*>).dismiss(this)
            }
        }
    }

    fun dismissToResult() {
        if (fragment is BottomSheetDialogFragment<*>) {
            (fragment as BottomSheetDialogFragment<*>).dismiss()
        }
    }
}
package pet.perpet.equal.presentation.more.viewmodel.payment

import android.os.Bundle
import android.text.Spanned
import android.view.View
import androidx.fragment.app.Fragment
import pet.perpet.equal.R
import pet.perpet.equal.presentation.dispatchDismissToResult
import pet.perpet.equal.presentation.getColor
import pet.perpet.equal.support.navigation.showPolicyInfo
import pet.perpet.framework.fragment.BottomSheetDialogFragment
import pet.perpet.framework.fragment.DialogFragment
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.util.http.HtmlFactory

class BottomPaymentTermViewModel(fragment: Fragment) : UseViewModel(fragment) {

    var allClick: Boolean = false

    var checkUse: Boolean = false
    val viewComment: Spanned?
        get() = getString(R.string.bottom_comment_7).orEmpty().run { HtmlFactory.fromHtml(this) }


    var termUse: Boolean = false

    var infoUse: Boolean = false

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

    //======================================================================
    // Public Methods
    //======================================================================

    fun onAllClick(view: View) {
        if (allClick.not()) {
            termUse = true
            infoUse = true
            allClick = true
            checkUse = true

        } else {
            termUse = false
            infoUse = false
            allClick = false
            checkUse = false
        }

        executeViewBinding()
    }

    fun onTermClick(view: View) {
        activity?.showPolicyInfo(
            getString(R.string.bottom_comment_11), getString(
                R.string.term_url
            )
        )
    }

    fun onTermItemClick(view: View) {
        termUse = termUse.not()
        allClick = termUse && infoUse
        checkUse = termUse && infoUse
        executeViewBinding()
    }

    fun onInfoClick(view: View) {
        activity?.showPolicyInfo(
            getString(R.string.bottom_comment_10), getString(
                R.string.info_url
            )
        )
    }

    fun onInfoItemClick(view: View) {
        infoUse = infoUse.not()
        allClick = termUse && infoUse
        checkUse = termUse && infoUse
        executeViewBinding()
    }

    fun onClick(view: View) {
        fragment?.dispatchDismissToResult(true)
    }

    fun onNegativeClick(view: View) {
        Bundle().apply {
            this.putBoolean("success", false)
        }.run {
            (fragment as BottomSheetDialogFragment<*>).dismiss()
        }
    }

}
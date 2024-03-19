package pet.perpet.equal.presentation.sign.viewmodel

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import pet.perpet.equal.presentation.dispatchDismissToResult
import pet.perpet.equal.presentation.sign.fragment.BottomBirthFragmentArgs
import pet.perpet.framework.fragment.BottomSheetDialogFragment
import pet.perpet.framework.fragment.UseViewModel

class BottomBirthViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Variables
    //======================================================================

    val args by lazy {
        BottomBirthFragmentArgs.fromBundle(arguments)
    }

    var year: Int = 0
    var month: Int = 0

    //======================================================================
    // Public Methods
    //======================================================================


    fun onClick(view: View) {
        fragment?.dispatchDismissToResult("$year-$month")
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
}
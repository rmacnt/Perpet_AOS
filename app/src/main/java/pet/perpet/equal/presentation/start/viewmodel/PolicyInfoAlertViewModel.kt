package pet.perpet.equal.presentation.start.viewmodel

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import pet.perpet.data.nonnull
import pet.perpet.equal.presentation.start.fragment.PolicyInfoAlertFragmentArgs
import pet.perpet.framework.fragment.BottomSheetDialogFragment
import pet.perpet.framework.fragment.UseViewModel

class PolicyInfoAlertViewModel (fragment: Fragment) : UseViewModel(fragment) {


    //======================================================================
    // Private Variables
    //======================================================================

    private val args by lazy {
        PolicyInfoAlertFragmentArgs.fromBundle(arguments)
    }

    val titie: String?
        get() = args.title?.nonnull()

    val url: String?
        get() = args.url?.nonnull()

    //======================================================================
    // Public Methods
    //======================================================================

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
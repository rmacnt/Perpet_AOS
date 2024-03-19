package pet.perpet.equal.presentation.more.viewmodel

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import pet.perpet.data.nonnull
import pet.perpet.equal.presentation.more.fragment.MoreWebFragmentArgs
import pet.perpet.framework.fragment.BottomSheetDialogFragment
import pet.perpet.framework.fragment.UseViewModel

class MoreWebViewModel (fragment: Fragment) : UseViewModel(fragment) {


    //======================================================================
    // Private Variables
    //======================================================================

    private val args by lazy {
        MoreWebFragmentArgs.fromBundle(arguments)
    }

    val content: String?
        get() = args.url.nonnull()


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
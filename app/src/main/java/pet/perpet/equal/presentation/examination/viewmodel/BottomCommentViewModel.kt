package pet.perpet.equal.presentation.examination.viewmodel

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import pet.perpet.data.nonnull
import pet.perpet.equal.R
import pet.perpet.equal.presentation.examination.fragment.BottomCommentFragmentArgs
import pet.perpet.equal.presentation.examination.fragment.ExaminationHealthResultDetailFragmentArgs
import pet.perpet.framework.fragment.BottomSheetDialogFragment
import pet.perpet.framework.fragment.UseViewModel

class BottomCommentViewModel (fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    private val args by lazy {
        BottomCommentFragmentArgs.fromBundle(arguments)
    }


    val comment: String?
        get() = String.format(getString(R.string.examination_comment9) , args.name.nonnull())


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
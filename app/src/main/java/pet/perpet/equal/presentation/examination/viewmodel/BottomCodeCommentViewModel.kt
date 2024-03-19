package pet.perpet.equal.presentation.examination.viewmodel

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import pet.perpet.framework.fragment.BottomSheetDialogFragment
import pet.perpet.framework.fragment.UseViewModel

class BottomCodeCommentViewModel (fragment: Fragment) : UseViewModel(fragment) {


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
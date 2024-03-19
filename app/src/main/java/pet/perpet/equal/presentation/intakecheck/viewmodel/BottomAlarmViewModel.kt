package pet.perpet.equal.presentation.intakecheck.viewmodel

import android.view.View
import androidx.fragment.app.Fragment
import pet.perpet.domain.UserStore
import pet.perpet.equal.presentation.dispatchDismissToResult
import pet.perpet.equal.presentation.intakecheck.fragment.BottomAlarmFragmentArgs
import pet.perpet.equal.presentation.intakecheck.model.IntakeAlarm
import pet.perpet.framework.fragment.BottomSheetDialogFragment
import pet.perpet.framework.fragment.UseViewModel

class BottomAlarmViewModel  (fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Variables
    //======================================================================

    val args by lazy {
        BottomAlarmFragmentArgs.fromBundle(arguments)
    }

    var type: Int = 0
    var time: Int = 0

    //======================================================================
    // Public Methods
    //======================================================================

    fun onAlarmCloseClick(view: View) {

        fragment?.dispatchDismissToResult(IntakeAlarm(-1 , -1, UserStore.userInfo?.id))
    }


    fun onNegativeClick(view: View) {
        if (fragment is BottomSheetDialogFragment<*>) {
            (fragment as BottomSheetDialogFragment<*>).dismiss()
        }
    }

    fun onAlarmStartClick(view: View) {
        fragment?.dispatchDismissToResult(IntakeAlarm(type , (time+1), UserStore.userInfo?.id))
    }
}
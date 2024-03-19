package pet.perpet.equal.presentation.intakecheck.viewmodel

import android.view.View
import androidx.fragment.app.Fragment
import pet.perpet.framework.fragment.UseViewModel

class IntakeCheckAlarmListViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Public Methods
    //======================================================================

    fun onFinishClick(view: View) {
        activity?.let {
            it.finish()
        }

    }
}
package pet.perpet.equal.presentation.more.viewmodel

import android.view.View
import androidx.fragment.app.Fragment
import pet.perpet.equal.R
import pet.perpet.equal.presentation.goWebInfo
import pet.perpet.framework.fragment.UseViewModel

class CustomerCenterViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Public Methods
    //======================================================================


    fun onFinishClick(view: View) {
        activity?.let {
            it.finish()
        }
    }

    fun onQnaClick(view: View) {
        activity?.let {
            it.goWebInfo(view.tag as String ,getString(R.string.qna_url))

            it.finish()
        }
    }


    fun onChannelTalkClick(view: View) {
        executeViewBinding()
    }

}
package pet.perpet.equal.presentation.more.viewmodel

import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import pet.perpet.equal.R
import pet.perpet.equal.presentation.goCompanyInfo
import pet.perpet.equal.presentation.goHome
import pet.perpet.equal.presentation.goPolicy
import pet.perpet.equal.presentation.goSettingAlram
import pet.perpet.framework.fragment.UseViewModel


class SettingViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    var result:Boolean = false


    //======================================================================
    // Public Methods
    //======================================================================

    fun onSubmitClick(view: View) {
        context?.let {
            activity?.finish()
        }
    }

    fun onHomeClick(view: View) {
        context?.let {
            it.goHome()
        }
    }

    fun onOpenSourceClick(view: View) {
        activity?.let {
            OssLicensesMenuActivity.setActivityTitle(getString(R.string.setting_comment4))
            val intent = Intent(it ,OssLicensesMenuActivity::class.java )
            it.startActivity(intent)
        }
    }

    fun onPolicyClick(view: View) {
        activity?.let {
            it.goPolicy()
        }
    }


    fun onFinishClick(view: View) {
        activity?.let {
            it.finish()
        }
    }


    fun onCompanyClick(view: View) {
        activity?.let {
            it.goCompanyInfo()
        }
    }

    fun onAlramClick(view: View) {
        context?.let {
            it.goSettingAlram()
        }
    }


}
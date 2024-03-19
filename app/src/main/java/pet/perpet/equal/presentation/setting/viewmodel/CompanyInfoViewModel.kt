package pet.perpet.equal.presentation.setting.viewmodel

import android.text.Spanned
import android.view.View
import androidx.fragment.app.Fragment
import pet.perpet.equal.R
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.util.http.HtmlFactory

class CompanyInfoViewModel (fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    var result:Boolean = false

    val detail: Spanned?
        get() = getString(R.string.setting_comment27)
            .orEmpty().run { HtmlFactory.fromHtml(this) }


    //======================================================================
    // Public Methods
    //======================================================================



    fun onFinishClick(view: View) {
        activity?.let {
            it.finish()
        }
    }

    fun onTermClick(view: View) {
        activity?.let {
            it.finish()
        }
    }

}
package pet.perpet.equal.presentation.setting.viewmodel

import android.view.View
import androidx.fragment.app.Fragment
import pet.perpet.equal.presentation.setting.fragment.InfoWebFragmentArgs
import pet.perpet.framework.fragment.UseViewModel

class InfoWebViewModel (fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    val args by lazy {
        InfoWebFragmentArgs.fromBundle(arguments)
    }

    val content: String?
        get() = args.url

    val title: String?
        get() = args.title


    //======================================================================
    // Public Methods
    //======================================================================



    fun onFinishClick(view: View) {
        activity?.let {
            it.finish()
        }
    }

}
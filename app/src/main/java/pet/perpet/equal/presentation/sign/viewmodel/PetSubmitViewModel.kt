package pet.perpet.equal.presentation.sign.viewmodel

import android.view.View

class PetSubmitViewModel() {

    //======================================================================
    // Variables
    //======================================================================

    private var notify: ((value: String) -> Unit)? = null


    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: ((value: String) -> Unit)?) {
        notify = value
    }


    fun onSubmitClick(view: View?) {
        notify?.let {call ->
            call(view?.tag.toString())
        }
    }
}

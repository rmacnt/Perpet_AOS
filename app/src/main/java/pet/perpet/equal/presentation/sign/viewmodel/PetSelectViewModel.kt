package pet.perpet.equal.presentation.sign.viewmodel

import android.view.View
import pet.perpet.equal.presentation.sign.model.PetSelect

class PetSelectViewModel(var model: PetSelect? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val petSelect: Int
        get() = when (model?.petSelect) {
            "dog" -> 1
            "cat" -> 2
            else -> 0
        }

    private var notify: ((value: String) -> Unit)? = null

    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: ((value: String) -> Unit)?) {
        notify = value
    }

    fun onTypeClick(view: View?) {
        notify?.let {call->
            call(when (view?.tag.toString()) {
                "1" -> {
                    "dog"
                }
                "2" -> {
                    "cat"
                }
                else -> "dog"
            })
        }
    }

}
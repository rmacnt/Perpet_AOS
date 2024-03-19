package pet.perpet.equal.presentation.sign.viewmodel

import pet.perpet.equal.presentation.sign.model.PetName

class PetNameViewModel(var model: PetName? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val petName: String?
        get() = model?.petName

    val error: Boolean?
        get() = model?.error

    private var notify: ((value: String) -> Unit)? = null

    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: ((value: String) -> Unit)?) {
        notify = value
    }

    fun onNameTextChange(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {

        notify?.let {call->
            call(text.toString())
        }
    }
}
package pet.perpet.equal.presentation.home.viewmodel

import android.content.Context
import android.view.View
import pet.perpet.domain.model.pet.Pet
import pet.perpet.equal.presentation.sign.Channel

open class MainItemViewModel  (var model: Pet? = null) {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var context: (() -> Context)

    val name: String?
        get() = model?.name

    val mainType: Boolean?
        get() = model?.main_yn == "Y"

    private var notify: ((value: Pet) -> Unit)? = null

    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: ((value: Pet) -> Unit)?) {
        notify = value
    }

    open fun onClick(view: View) {
        model?.let {
            notify?.let {call->
                call(it)
            }

        }
    }
}
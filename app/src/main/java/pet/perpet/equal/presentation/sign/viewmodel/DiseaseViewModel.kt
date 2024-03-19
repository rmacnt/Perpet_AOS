package pet.perpet.equal.presentation.sign.viewmodel

import android.content.Context
import android.view.View
import pet.perpet.domain.model.profile.Disease
import pet.perpet.framework.util.Logger

abstract class DiseaseViewModel (private var model: Disease? = null) {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var context: (() -> Context)

    val name: String?
        get() = model?.name

    private var notify: ((value: Disease) -> Unit)? = null

    //======================================================================
    // Public Methods
    //======================================================================

    fun bind(model: Disease?) {
        this.model = model
        Logger.d("DiseaseViewModel", "bind > ${model}")
    }

    fun notify(value: ((value: Disease) -> Unit)?) {
        notify = value
    }


    open fun onClick(view: View) {
        Logger.d("DiseaseViewModel", "onClick > ${model}")
        model?.let {
            notify?.let {call->
                call(it)
            }

        }
    }
}
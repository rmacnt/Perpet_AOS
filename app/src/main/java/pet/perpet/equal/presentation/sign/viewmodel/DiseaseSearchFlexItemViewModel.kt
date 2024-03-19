package pet.perpet.equal.presentation.sign.viewmodel

import android.content.Context
import android.view.View
import pet.perpet.domain.model.profile.Disease
import pet.perpet.framework.util.Logger

open class DiseaseSearchFlexItemViewModel (var model: Disease? = null) {

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

    fun notify(value: ((value: Disease) -> Unit)?) {
        notify = value
    }

    open fun onClick(view: View) {
        Logger.d("DiseaseSearchFlexItemViewModel", "onClick > ${model}")
        model?.let {
            notify?.let {call->
                call(it)
            }

        }
    }
}
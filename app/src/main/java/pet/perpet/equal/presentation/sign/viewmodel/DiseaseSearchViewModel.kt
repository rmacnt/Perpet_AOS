package pet.perpet.equal.presentation.sign.viewmodel

import android.content.Context
import android.view.View
import pet.perpet.data.nonnull
import pet.perpet.domain.model.profile.Disease
import pet.perpet.framework.util.Logger

open class DiseaseSearchViewModel (var model: Disease? = null) {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var context: (() -> Context)

    val name: String?
        get() = model?.name

    var isChecked: Boolean
        get() = model?.isChecked.nonnull()
        set(value) {
            model?.isChecked = value
        }

    private var notify: ((value: Disease) -> Unit)? = null


    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: ((value: Disease) -> Unit)?) {
        notify = value
    }

    open fun onClick(view: View) {
        Logger.d("DiseaseSearchViewModel", "onClick > ${model}")
        model?.let {
            notify?.let {call->
                call(it)
            }

        }
    }
}
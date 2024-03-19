package pet.perpet.equal.presentation.sign.viewmodel

import android.content.Context
import android.view.View
import pet.perpet.domain.model.profile.Allergy
import pet.perpet.equal.presentation.sign.Channel
import pet.perpet.framework.util.Logger

open class SearchFlexItemViewModel(var model: Allergy? = null) {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var context: (() -> Context)

    val name: String?
        get() = model?.name

    private var notify: ((value: Allergy) -> Unit)? = null

    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: ((value: Allergy) -> Unit)?) {
        notify = value
    }

    open fun onClick(view: View) {
        Logger.d("SearchFlexItemViewModel", "onClick > ${model}")
        model?.let {
            notify?.let {call->
                call(it)
            }

        }
    }
}
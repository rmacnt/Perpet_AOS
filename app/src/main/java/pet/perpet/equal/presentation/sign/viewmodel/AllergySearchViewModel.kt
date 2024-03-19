package pet.perpet.equal.presentation.sign.viewmodel

import android.content.Context
import android.view.View
import pet.perpet.data.nonnull
import pet.perpet.domain.model.address.ListAddress
import pet.perpet.domain.model.profile.Allergy
import pet.perpet.equal.presentation.sign.Channel
import pet.perpet.framework.util.Logger

open class AllergySearchViewModel (var model: Allergy? = null) {

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

    private var notify: ((value: Allergy) -> Unit)? = null


    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: ((value: Allergy) -> Unit)?) {
        notify = value
    }

    open fun onClick(view: View) {
        Logger.d("AllergySearchViewModel", "onClick > ${model}")
        model?.let {
            notify?.let {call->
                call(it)
            }

        }
    }
}
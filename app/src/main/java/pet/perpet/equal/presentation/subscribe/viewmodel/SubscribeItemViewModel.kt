package pet.perpet.equal.presentation.subscribe.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Checkable
import pet.perpet.data.nonnull
import pet.perpet.domain.model.address.ListAddress
import pet.perpet.domain.model.profile.Allergy
import pet.perpet.equal.presentation.sign.Channel
import pet.perpet.framework.util.Logger

open class SubscribeItemViewModel(var model: ListAddress? = null) {

    //======================================================================
    // Variables
    //======================================================================


    lateinit var context: (() -> Context)

    val recipient: String?
        get() = model?.recipient

    val address: String?
        get() = model?.address

    var isChecked: Boolean
        get() = model?.isChecked.nonnull()
        set(value) {
            model?.isChecked = value
        }

    private var notify: ((value: ListAddress) -> Unit)? = null

    //======================================================================
    // Public Methods
    //======================================================================


    fun notify(value: ((value: ListAddress) -> Unit)?) {
        notify = value
    }

    open fun onClick(view: View) {
        Logger.d("SubscribeItemViewModel", "onClick > ${model} onChecked > ${model?.isChecked}")
        model?.let {
            isChecked = isChecked.not()
            this.model = model

            notify?.let {call->
                call(it)
            }

        }
    }
}
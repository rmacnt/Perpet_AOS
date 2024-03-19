package pet.perpet.equal.presentation.subscribe.viewmodel

import android.content.Context
import android.view.View
import pet.perpet.data.nonnull
import pet.perpet.domain.model.address.ListAddress
import pet.perpet.domain.model.card.CardList
import pet.perpet.equal.presentation.sign.Channel
import pet.perpet.framework.util.Logger

open class SearchCardItemVIewModel  (var model: CardList? = null) {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var context: (() -> Context)

    val name: String?
        get() = "${model?.card_name} / ${model?.card_number}"


    var isChecked: Boolean
        get() = model?.isChecked.nonnull()
        set(value) {
            model?.isChecked = value
        }

    private var notify: ((value: CardList) -> Unit)? = null

    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: ((value: CardList) -> Unit)?) {
        notify = value
    }

    open fun onClick(view: View) {
        Logger.d("SubscribeItemViewModel", "onClick > ${model}")
        model?.let {
            isChecked = isChecked.not()
            this.model = model

            notify?.let {call->
                call(it)
            }

        }
    }
}
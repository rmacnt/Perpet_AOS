package pet.perpet.equal.presentation.more.viewmodel

import android.view.View
import pet.perpet.equal.presentation.getSubscribeDetail
import pet.perpet.equal.presentation.more.model.OrderInfo
import pet.perpet.framework.nonnull

open class OrderInfoViewModel  (var model: OrderInfo? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val title: String?
        get() = model?.name.nonnull()

    private var notify: (() -> Unit)? = null

    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: (() -> Unit)?) {
        notify = value
    }

    fun onClick(view: View) {
        model?.orderInfo?.let {
            view.context.getSubscribeDetail(it.order_id.nonnull().toString(), it.pet_name.nonnull())

            notify?.let {call->
                call()
            }
        }


    }

}
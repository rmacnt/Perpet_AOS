package pet.perpet.equal.support.push

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import pet.perpet.equal.presentation.base.bus.RxBus2
import pet.perpet.equal.support.push.model.MessageBody

object Push {

    fun newMessage(value: MessageBody) {
        RxBus2.post(value)
    }

    fun observeMessage(fragment: Fragment, callback: (message: MessageBody?) -> Unit) {
        RxBus2.subscribe(fragment, MessageBody::class.java, callback)
    }

    fun observeMessage(fragment: FragmentActivity, callback: (message: MessageBody?) -> Unit) {
        RxBus2.subscribe(fragment, MessageBody::class.java, callback)
    }

    data class Event(val finishTutorMain: Boolean, val finishTueteeMain: Boolean)
}

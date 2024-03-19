package pet.perpet.equal.support.push.process

import android.content.Context
import android.net.Uri
import pet.perpet.equal.presentation.getSubscribeDetail
import pet.perpet.equal.support.push.model.MessageBody
import pet.perpet.equal.support.push.presentation.PushProcess

class PushSubscriptionDetailsProcess (body: MessageBody) : PushProcess(body) {

    override fun observable(context: Context) {

        val subscriptionId = Uri.parse(body.link.orEmpty()).getQueryParameter("subscriptionId")
        subscriptionId?.let {
            context.getSubscribeDetail(
                orderId = it,
                name = ""
            )
        }
        finish(context)
    }
}
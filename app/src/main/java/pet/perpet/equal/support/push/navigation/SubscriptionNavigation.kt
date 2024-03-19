package pet.perpet.equal.support.push.navigation

import android.content.Context
import android.net.Uri
import pet.perpet.domain.model.notification.PushList
import pet.perpet.equal.presentation.getSubscribeDetail

class SubscriptionNavigation  (args: PushList) : Navigation(args) {

    override fun isValid(args: PushList): Boolean {
        return true
    }

    override fun go(context: Context) {
        val subscriptionId = Uri.parse(args.link.orEmpty()).getQueryParameter("subscriptionId")
        subscriptionId?.let {
            context.getSubscribeDetail(
                orderId = it,
                name = ""
            )
        }
    }
}
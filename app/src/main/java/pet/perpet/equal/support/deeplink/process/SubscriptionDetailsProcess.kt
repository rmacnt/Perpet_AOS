package pet.perpet.equal.support.deeplink.process

import android.content.Context
import pet.perpet.equal.presentation.getSubscribeDetail
import pet.perpet.equal.support.deeplink.model.BaseArgument

class SubscriptionDetailsProcess  (body: BaseArgument) : DeepLinkProcess(body) {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun observable(context: Context) {
        if(body.subscriptionId.isNotEmpty()) {
            context.getSubscribeDetail(
                orderId = body.subscriptionId,
                name = ""
            )
        }

        finish(context)
    }

}
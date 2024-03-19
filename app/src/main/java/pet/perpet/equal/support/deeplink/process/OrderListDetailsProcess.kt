package pet.perpet.equal.support.deeplink.process

import android.content.Context
import pet.perpet.equal.presentation.goOrderHistory
import pet.perpet.equal.support.deeplink.model.BaseArgument

class OrderListDetailsProcess  (body: BaseArgument) : DeepLinkProcess(body) {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun observable(context: Context) {
        context.goOrderHistory()
        finish(context)
    }

}
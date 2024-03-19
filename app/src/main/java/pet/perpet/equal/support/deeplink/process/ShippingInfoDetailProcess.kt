package pet.perpet.equal.support.deeplink.process

import android.content.Context
import pet.perpet.equal.presentation.goShippingManagement
import pet.perpet.equal.support.deeplink.model.BaseArgument

class ShippingInfoDetailProcess   (body: BaseArgument) : DeepLinkProcess(body) {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun observable(context: Context) {
        context.goShippingManagement()
        finish(context)
    }

}
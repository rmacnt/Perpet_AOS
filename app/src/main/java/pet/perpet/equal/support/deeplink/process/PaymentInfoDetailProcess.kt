package pet.perpet.equal.support.deeplink.process

import android.content.Context
import pet.perpet.equal.presentation.goPayment
import pet.perpet.equal.support.deeplink.model.BaseArgument

class PaymentInfoDetailProcess (body: BaseArgument) : DeepLinkProcess(body) {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun observable(context: Context) {
        context.goPayment()
        finish(context)
    }

}
package pet.perpet.equal.support.deeplink.process

import android.content.Context
import pet.perpet.equal.presentation.goMypage
import pet.perpet.equal.support.deeplink.model.BaseArgument

class AccountDetailProcess  (body: BaseArgument) : DeepLinkProcess(body) {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun observable(context: Context) {
        context.goMypage()
        finish(context)
    }

}
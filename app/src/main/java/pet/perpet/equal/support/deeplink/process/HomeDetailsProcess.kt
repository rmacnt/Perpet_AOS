package pet.perpet.equal.support.deeplink.process

import android.content.Context
import pet.perpet.equal.presentation.goHome
import pet.perpet.equal.support.deeplink.model.BaseArgument

class HomeDetailsProcess(body: BaseArgument) : DeepLinkProcess(body) {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun observable(context: Context) {
        context.goHome()
        finish(context)
    }
}
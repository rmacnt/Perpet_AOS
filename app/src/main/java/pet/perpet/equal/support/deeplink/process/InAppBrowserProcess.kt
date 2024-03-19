package pet.perpet.equal.support.deeplink.process

import android.content.Context
import pet.perpet.equal.support.deeplink.model.BaseArgument

class InAppBrowserProcess(body: BaseArgument) : DeepLinkProcess(body) {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun observable(context: Context) {
        finish(context)
    }
}
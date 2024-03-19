package pet.perpet.equal.support.deeplink.process

import android.content.Context
import pet.perpet.equal.presentation.goMore
import pet.perpet.equal.support.deeplink.model.BaseArgument

class MoreDetailsProcess  (body: BaseArgument) : DeepLinkProcess(body) {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun observable(context: Context) {
        context.goMore()
        finish(context)
    }

}
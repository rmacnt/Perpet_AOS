package pet.perpet.equal.support.deeplink.process

import android.content.Context
import pet.perpet.equal.presentation.goInbox
import pet.perpet.equal.support.deeplink.model.BaseArgument

class NotificationsDetailProcess (body: BaseArgument) : DeepLinkProcess(body) {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun observable(context: Context) {
        context.goInbox()
        finish(context)
    }

}
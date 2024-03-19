package pet.perpet.equal.support.push.process

import android.content.Context
import pet.perpet.equal.R
import pet.perpet.equal.presentation.goOrderHistory
import pet.perpet.equal.presentation.goWebInfo
import pet.perpet.equal.support.push.model.MessageBody
import pet.perpet.equal.support.push.presentation.PushProcess

class InAppBrowserProcess(body: MessageBody) : PushProcess(body) {

    override fun observable(context: Context) {
        context.goWebInfo("" ,body.link.orEmpty())
        finish(context)
    }
}
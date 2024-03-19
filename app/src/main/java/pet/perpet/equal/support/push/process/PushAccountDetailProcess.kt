package pet.perpet.equal.support.push.process

import android.content.Context
import pet.perpet.equal.presentation.goMypage
import pet.perpet.equal.support.push.model.MessageBody
import pet.perpet.equal.support.push.presentation.PushProcess

class PushAccountDetailProcess (body: MessageBody) : PushProcess(body) {

    override fun observable(context: Context) {
        context.goMypage()
        finish(context)
    }
}
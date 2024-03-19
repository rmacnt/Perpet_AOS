package pet.perpet.equal.support.push.process

import android.content.Context
import android.util.Log
import pet.perpet.equal.presentation.goIntakeCheck
import pet.perpet.equal.support.push.model.MessageBody
import pet.perpet.equal.support.push.presentation.PushProcess

class PushIntakeDetailsProcess(body: MessageBody) : PushProcess(body) {

    override fun observable(context: Context) {
        context.goIntakeCheck()
        finish(context)
    }
}
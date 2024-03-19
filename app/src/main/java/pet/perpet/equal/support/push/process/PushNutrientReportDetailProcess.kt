package pet.perpet.equal.support.push.process

import android.content.Context
import pet.perpet.equal.presentation.goIntakeCheck
import pet.perpet.equal.presentation.goSearch
import pet.perpet.equal.support.deeplink.model.BaseArgument
import pet.perpet.equal.support.push.model.MessageBody
import pet.perpet.equal.support.push.presentation.PushProcess

class PushNutrientReportDetailProcess (body: MessageBody) : PushProcess(body) {

    override fun observable(context: Context) {
        context.goSearch(type = 1)
        finish(context)
    }
}
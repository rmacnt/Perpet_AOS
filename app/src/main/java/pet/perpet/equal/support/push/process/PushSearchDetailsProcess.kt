package pet.perpet.equal.support.push.process

import android.content.Context
import android.net.Uri
import pet.perpet.equal.presentation.goSearch
import pet.perpet.equal.support.push.model.MessageBody
import pet.perpet.equal.support.push.presentation.PushProcess

class PushSearchDetailsProcess(body: MessageBody) : PushProcess(body) {

    override fun observable(context: Context) {

        val keyword = Uri.parse(body.link.orEmpty()).getQueryParameter("keyword")
        keyword?.let {
            context.goSearch(it)
        }
        finish(context)
    }
}
package pet.perpet.equal.support.push.process

import android.content.Context
import android.content.Intent
import android.net.Uri
import pet.perpet.equal.support.push.model.MessageBody
import pet.perpet.equal.support.push.presentation.PushProcess

class ExternalBrowserProcess (body: MessageBody) : PushProcess(body) {

    override fun observable(context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(body.link.orEmpty()))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.setPackage("com.android.chrome")
        context.startActivity(intent)

        finish(context)
    }
}
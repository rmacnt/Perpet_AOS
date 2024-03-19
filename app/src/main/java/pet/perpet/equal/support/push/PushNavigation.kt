package pet.perpet.equal.support.push

import android.content.Context
import android.content.Intent
import pet.perpet.data.nonnull
import pet.perpet.domain.gson
import pet.perpet.domain.toJson
import pet.perpet.equal.support.push.model.MessageBody
import pet.perpet.equal.support.push.model.PushMessage
import pet.perpet.equal.support.push.presentation.PushLinkActivity

object PushNavigation {

    //======================================================================
    // Variable
    //======================================================================

    const val BODY = "MESSAGE_BODY"

    const val TYPE = "MESSAGE_TYPE"

    const val LAUNCHER = "LAUNCHER"

    //======================================================================
    // Public Methods
    //======================================================================

}

fun Intent.getMessageBody(): MessageBody? {
    try {
        val v = this.getStringExtra(PushNavigation.BODY)
        val type: PushMessage.Type? = this.getSerializableExtra(PushNavigation.TYPE)?.run {
            if (this is PushMessage.Type) {
                this
            } else {
                null
            }
        }
        val instance = gson.fromJson(v, MessageBody::class.java)
        type?.run {
            instance?.type = this
        }
        return instance
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

fun Intent.getLaunch(): Boolean {
    try {
        val v = this.getBooleanExtra(PushNavigation.LAUNCHER, false)
        return v.nonnull()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return false
}

fun Context.createPushMessageIntent(type: PushMessage.Type?, body: MessageBody?): Intent {
    val i = Intent(this, PushLinkActivity::class.java)
    i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
    i.putExtra(PushNavigation.TYPE, type)
    i.putExtra(PushNavigation.LAUNCHER, false)
    body?.let {
        i.putExtra(PushNavigation.BODY, it.toJson())
    }
    return i
}

fun Context.createPushMessageIntent(type: PushMessage.Type, body: String?): Intent {
    val i = Intent(this, PushLinkActivity::class.java)
    i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
    i.putExtra(PushNavigation.TYPE, type)
    i.putExtra(PushNavigation.LAUNCHER, true)
    body?.run {
        i.putExtra(PushNavigation.BODY, this)
    }
    return i
}

fun Context.goPushMessage(type: PushMessage.Type, body: String?) {
    val i = this.createPushMessageIntent(type, body)
    this.startActivity(i)
}
package pet.perpet.equal.support.push.behavior

import android.content.Context
import android.content.Intent
import com.google.firebase.messaging.RemoteMessage
import pet.perpet.equal.support.push.BadgeStore
import pet.perpet.equal.support.logger.AppLogger
import pet.perpet.equal.support.logger.w
import pet.perpet.equal.support.push.Push
import pet.perpet.equal.support.push.createPushMessageIntent
import pet.perpet.equal.support.push.model.PushMessage
import pet.perpet.equal.support.push.update

object BehaviorFactory {

    //======================================================================
    // Public Methods
    //======================================================================

    @JvmStatic
    fun create(rm: RemoteMessage): Behavior {
        val message = PushMessage.parse(rm)
        return DefaultBehavior(message)
    }
    //======================================================================
    // DefaultBehavior
    //======================================================================

    class DefaultBehavior(push: PushMessage) : Behavior(push) {

        override fun onAfterProcess(context: Context) {
            body?.let {
                BadgeStore.update()
                Push.newMessage(it)
            }
        }

        override fun createRunningIntent(context: Context): Intent? {
            AppLogger.Tag.PUSH.w("createRunningIntent >> body : $body")
            return context.createPushMessageIntent(toType, body)
        }

        override fun createLaunchIntent(context: Context): Intent? {
            AppLogger.Tag.PUSH.w("createLaunchIntent >> body : $body")
            return context.createPushMessageIntent(toType, body)
        }
    }
}
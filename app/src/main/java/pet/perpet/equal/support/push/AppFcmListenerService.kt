package pet.perpet.equal.support.push

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.zoyi.channel.plugin.android.ChannelIO
import pet.perpet.domain.TokenStore
import pet.perpet.equal.support.logger.AppLogger
import pet.perpet.equal.support.push.behavior.BehaviorFactory


class AppFcmListenerService : FirebaseMessagingService() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onMessageReceived(rm: RemoteMessage) {
        try {
            AppLogger.w(AppLogger.Tag.PUSH, "onMessageReceived > $rm")

            rm.let {
                val pushMessage = rm.data

                if (ChannelIO.isChannelPushNotification(pushMessage)) {
                    ChannelIO.receivePushNotification(application, pushMessage)
                    BadgeStore.update()
                } else {
                    BehaviorFactory.create(it).process(this)
                }
            }

        } catch (e: Exception) {
            AppLogger.printStackTrace(e)
        }
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
        AppLogger.w(AppLogger.Tag.PUSH, "onDeletedMessages")
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)


        token.takeIf {
            it.isNotEmpty()
        }?.run {
        }
        AppLogger.w(AppLogger.Tag.PUSH, "onNewToken > $token")
        TokenStore.syncDeviceToken(token)
        ChannelIO.initPushToken(token)

    }
}


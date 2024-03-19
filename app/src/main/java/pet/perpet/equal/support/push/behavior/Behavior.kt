package pet.perpet.equal.support.push.behavior

import android.Manifest
import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.media.AudioAttributes
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import pet.perpet.data.nonnull
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getString
import pet.perpet.equal.presentation.notification.MessageListActivity
import pet.perpet.equal.support.ApplicationBehavior
import pet.perpet.equal.support.logger.AppLogger
import pet.perpet.equal.support.push.AppFcmListenerService
import pet.perpet.equal.support.push.model.MessageBody
import pet.perpet.equal.support.push.model.PushMessage
import pet.perpet.framework.util.Utils


abstract class Behavior(private val push: PushMessage) {

    //======================================================================
    // Variables
    //======================================================================

    val body: MessageBody?
        get() = push.body

    val message: String?
        get() = push.message

    val toType: PushMessage.Type?
        get() = push.toType

    //======================================================================
    // Abstract Methods
    //======================================================================

    abstract fun createRunningIntent(context: Context): Intent?

    abstract fun createLaunchIntent(context: Context): Intent?

    abstract fun onAfterProcess(context: Context)

    //======================================================================
    // Public Methods
    //======================================================================

    @SuppressLint("WrongConstant", "UnspecifiedImmutableFlag")
    open fun createNotification(
        context: Context,
        intent: Intent,
        big: NotificationCompat.Style?
    ): Notification? {
        val requestID = System.currentTimeMillis().toInt()

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getActivity(context, requestID, intent, PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent.getActivity(context, requestID, intent, PendingIntent.FLAG_ONE_SHOT)
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setLargeIcon(
            BitmapFactory.decodeResource(
                context.resources,
                R.mipmap.ic_launcher
            )
        )
        builder.setShowWhen(true)
        builder.setContentTitle(push.title)
        builder.setContentText(push.message)
        builder.setTicker(push.message)
        builder.setWhen(System.currentTimeMillis())
        builder.setContentIntent(pendingIntent)
        builder.priority = NotificationCompat.PRIORITY_HIGH
        builder.setDefaults(0)
        builder.setAutoCancel(true)

        if (big != null) {
            builder.setStyle(big)
        }
        return builder.build()
    }

    open fun notificationId(): Int {
        return 1234234234
    }

    @Throws(Exception::class)
    fun process(context: AppFcmListenerService) {
        val type = push.type
        val bodyJson = push.body
        val title = push.title
        val message = push.message
        val parseType = PushMessage.Type.parse(type.nonnull())
        val runningProcess = ApplicationBehavior.isRunningProcess(context, context.packageName)

        log("title : " + title.nonnull())
        log("type : $type parseType : $parseType")
        log("body : $bodyJson")
        log("message : $message")
        log("runningProcess : $runningProcess")


        val intent = if (runningProcess) {
            createRunningIntent(context)
        } else {
            createLaunchIntent(context)
        }

        parseType.takeIf {
            it?.isShowNotification == true
        }?.run {
            if (isRunningNotification(context, this)) {
                with(NotificationManagerCompat.from(context)) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        val name = getString(R.string.app_name)
                        val descriptionText = getString(R.string.app_name)
                        val importance = NotificationManager.IMPORTANCE_HIGH
                        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                            description = descriptionText
                            setShowBadge(true)
                        }
                        createNotificationChannel(channel)
                    }
                    intent?.let {
                        val big = if (!bodyJson?.imageUrl.isNullOrEmpty()) {
                            createBigStyle(
                                bodyJson?.imageUrl.orEmpty(),
                                title.orEmpty(),
                                message.orEmpty()
                            )
                        } else {
                            null
                        }

                        val n = createNotification(context, it, big)
                        if (n != null) {
                            if (ActivityCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.POST_NOTIFICATIONS
                                ) != PackageManager.PERMISSION_GRANTED
                            ) {
                                return
                            }
                            notify(body?.type?.toNotificationId().nonnull(Int.MAX_VALUE), n)
                        }
                    }
                }
            }
        }
        onAfterProcess(context)
    }

    //======================================================================
    // Private Methods
    //======================================================================

    private fun createAudioAttributes(): AudioAttributes {
        return AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()
    }

    private fun createBigStyle(
        imageUrl: String,
        title: String,
        message: String
    ): NotificationCompat.BigPictureStyle? {
        try {
            AppLogger.w(
                AppLogger.Tag.PUSH,
                "createBigStyle >> image : $imageUrl, message : $message"
            )
            val big = NotificationCompat.BigPictureStyle()
            big.setBigContentTitle(title)
            big.setSummaryText(message)

            val image = Utils.getBitmap(imageUrl)
            big.bigLargeIcon(image)
            big.bigPicture(image)
            return big
        } catch (e: java.lang.Exception) {
            AppLogger.printStackTrace(e)
        }
        return null
    }

    private fun isRunningNotification(context: Context, type: PushMessage.Type): Boolean {
        fun isMessageTopActivity(context: Context): Boolean {
            val status = ApplicationBehavior.toRunningProcessStatus(context, context.packageName)
            val list = ApplicationBehavior.getRunningTaskInfoList(context)
            for (info in list!!) {
                if (status == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND && info.topActivity?.className == MessageListActivity::class.java.name) {
                    return true
                }
            }
            return false
        }
        if (isMessageTopActivity(context)) {
            return false
        }
        return true
    }

    private fun log(message: String) {
        AppLogger.d(AppLogger.Tag.PUSH, message)
    }

    companion object {

        const val CHANNEL_ID = "taling-channel"
    }
}
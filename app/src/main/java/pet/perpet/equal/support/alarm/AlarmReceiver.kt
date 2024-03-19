package pet.perpet.equal.support.alarm

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.gson.annotations.SerializedName
import pet.perpet.data.nonnull
import pet.perpet.domain.UserStore
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getString
import pet.perpet.equal.presentation.intakecheck.IntakeStore
import pet.perpet.equal.support.ApplicationBehavior
import pet.perpet.equal.support.push.behavior.Behavior
import pet.perpet.equal.support.push.createPushMessageIntent
import pet.perpet.equal.support.push.model.MessageBody
import pet.perpet.equal.support.push.model.PushMessage
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class AlarmReceiver : BroadcastReceiver() {

    @SuppressLint("SimpleDateFormat")
    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {context ->
            IntakeStore.intakeAlarm?.let {

                var intakeTime  = 0
                var intakeType  = 0
                it.forEach {
                    if(it.userId == UserStore.userInfo?.id) {
                        intakeTime =  if (it.type == 1) it.time.nonnull() + 12 else it.time.nonnull()
                        intakeType = it.type.nonnull()
                        return@forEach
                    }
                }
                val runningProcess = ApplicationBehavior.isRunningProcess(context, context.packageName)
                val messageBody = MessageBody(
                    title = getString(R.string.dialog_title),
                    body = getString(R.string.intake_check_comment32),
                    link = "equal-pet://navigation?scene=intake",
                    action = "intake",
                    null

                )
                val intentCall = if (runningProcess) {
                    context.createPushMessageIntent(PushMessage.Type.intake, messageBody)
                } else {
                    context.createPushMessageIntent(PushMessage.Type.intake, messageBody)
                }

                val now = System.currentTimeMillis()
                val date = Date(now)
                val hourFormat = SimpleDateFormat("HH")
                val minFormat = SimpleDateFormat("mm")

                val hourTime = hourFormat.format(date)
                val minTime = minFormat.format(date).toInt()


                if((intakeType != -1) && (hourTime.toInt() == intakeTime)) {
                   if(minTime < 3 ) {
                       with(NotificationManagerCompat.from(context)) {
                           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                               val name = getString(R.string.app_name)
                               val descriptionText = getString(R.string.app_name)
                               val importance = NotificationManager.IMPORTANCE_HIGH
                               val channel = NotificationChannel(Behavior.CHANNEL_ID, name, importance).apply {
                                   description = descriptionText
                               }
                               createNotificationChannel(channel)
                           }
                           intentCall.let {
                               val n = createNotification(context, it)
                               if (n != null) {
                                   if (ActivityCompat.checkSelfPermission(
                                           context,
                                           Manifest.permission.POST_NOTIFICATIONS
                                       ) != PackageManager.PERMISSION_GRANTED
                                   ) {
                                       return
                                   }
                                   notify(0, n)
                                   setReminder(context)
                               }
                           }
                       }
                   }
                }
            }
        }
    }

    fun createNotification(
        context: Context,
        intent: Intent,
    ): Notification? {
        val requestID = System.currentTimeMillis().toInt()

        val pendingIntent = PendingIntent.getActivity(
            context,
            requestID,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )
        val builder = NotificationCompat.Builder(context, Behavior.CHANNEL_ID)
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setLargeIcon(
            BitmapFactory.decodeResource(
                context.resources,
                R.mipmap.ic_launcher
            )
        )
        builder.setShowWhen(true)
        builder.setContentTitle(getString(R.string.dialog_title))
        builder.setContentText(getString(R.string.intake_check_comment32))
        builder.setWhen(System.currentTimeMillis())
        builder.setContentIntent(pendingIntent)
        builder.priority = NotificationCompat.PRIORITY_HIGH
        builder.setDefaults(0)
        builder.setAutoCancel(true)
        return builder.build()
    }

    fun setReminder(context: Context) {
        IntakeStore.intakeAlarm?.let {

            var intakeTime  = 0
            it.forEach {
                if(it.userId == UserStore.userInfo?.id) {
                    intakeTime =  if (it.type == 1) it.time.nonnull() + 12 else it.time.nonnull()
                    return@forEach
                }
            }
            val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val intent = Intent(context, AlarmReceiver::class.java)
            intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
            val pendingIntent = PendingIntent.getBroadcast(context, 7727, intent, IntakeStore.ALARM_FLAG)

            val calendar = Calendar.getInstance().apply {

                timeInMillis = System.currentTimeMillis()

                set(Calendar.HOUR_OF_DAY ,intakeTime)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                add(Calendar.DATE, 1)
            }

            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
        }
    }
}
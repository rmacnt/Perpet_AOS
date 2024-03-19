package pet.perpet.equal.support.alarm

import android.app.ActivityManager
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import pet.perpet.data.nonnull
import pet.perpet.domain.UserStore
import pet.perpet.equal.presentation.intakecheck.IntakeStore
import java.util.Calendar

class BootCompletedReceiver  : BroadcastReceiver()  {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {

            val manager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val hasPermission: Boolean = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                manager.canScheduleExactAlarms()
            } else {
                true
            }

            if(hasPermission && UserStore.userInfo != null) {

                IntakeStore.intakeAlarm?.let {

                    var intakeTime  = 0
                    it.forEach {
                        if(it.userId == UserStore.userInfo?.id) {
                            intakeTime =  if (it.type == 1) it.time.nonnull() + 12 else it.time.nonnull()
                            return@forEach
                        }
                    }

                    if(intakeTime != 0) {
                        val intent = Intent(context, AlarmReceiver::class.java)
                        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND)
                        val pendingIntent = PendingIntent.getBroadcast(context, 7727, intent, IntakeStore.ALARM_FLAG)

                        val calendar = Calendar.getInstance().apply {

                            timeInMillis = System.currentTimeMillis()

                            set(Calendar.HOUR_OF_DAY ,intakeTime)
                            set(Calendar.MINUTE, 0)
                            set(Calendar.SECOND, 0)
                            set(Calendar.MILLISECOND, 0)

                            if (before(Calendar.getInstance())) {
                                add(Calendar.DATE, 1)
                            }
                        }

                        manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
                    }
                }

            }

        }
    }

}
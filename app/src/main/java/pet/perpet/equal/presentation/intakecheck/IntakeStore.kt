package pet.perpet.equal.presentation.intakecheck

import android.app.PendingIntent
import android.os.Build
import pet.perpet.data.repository.base.datasource.CashDataSource
import pet.perpet.equal.presentation.intakecheck.model.IntakeAlarm

object IntakeStore {

    val intakeAlarm: ArrayList<IntakeAlarm>?
        get() {
            return intakeAlarmDataSource.get()
        }

    private val intakeAlarmDataSource: CashDataSource<ArrayList<IntakeAlarm>> by lazy {
        object : CashDataSource<ArrayList<IntakeAlarm>>() {
            override val key: String
                get() = "searchDataSource"
        }
    }

    fun sync(item: IntakeAlarm) {
        val array : ArrayList<IntakeAlarm> = arrayListOf()
        var user = false
        intakeAlarmDataSource.get()?.forEach {
            if(it.userId == item.userId) {
                user = true
                array.add(item)
            } else {
                array.add(it)
            }
        }
        if(user.not()) {
            array.add(item)
        }
        intakeAlarmDataSource.clear()
        intakeAlarmDataSource.save(array)
    }


    fun clear() {
        intakeAlarmDataSource.clear()
    }

    val ALARM_FLAG = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
    } else {
        PendingIntent.FLAG_UPDATE_CURRENT
    }
}
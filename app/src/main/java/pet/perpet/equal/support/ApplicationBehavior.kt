package pet.perpet.equal.support

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context

object ApplicationBehavior {

    @JvmStatic
    fun isRunningProcess(context: Context, packageName: String): Boolean {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val apps = am.runningAppProcesses
        for (info in apps) {
            if (info.processName == packageName) {
                return true
            }
        }
        return false
    }

    @JvmStatic
    fun toRunningProcessStatus(context: Context, packageName: String): Int {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val apps = am.runningAppProcesses
        for (info in apps) {
            if (info.processName == packageName) {
                return info.importance
            }
        }
        return Int.MIN_VALUE
    }

    @SuppressLint("ServiceCast")
    @JvmStatic
    fun getRunningTaskInfoList(context: Context): MutableList<ActivityManager.RunningTaskInfo>? {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return am.getRunningTasks(1000)
    }
}
package pet.perpet.equal.support.logger

import android.util.Log
import pet.perpet.equal.AppConfig.LOG_ENABLE
import java.util.*

object AppLogger {
    private const val LOG_FORMAT = "[%s]: %s"

    fun i(tag: String?, message: String?) {
        if (LOG_ENABLE && !message.isNullOrEmpty()) {
            Log.i(tag, message)
        }
    }

    fun d(tag: String?, message: String?) {
        if (LOG_ENABLE && !message.isNullOrEmpty()) {
            Log.d(tag, message)
        }
    }

    fun e(tag: String?, message: String?) {
        if (LOG_ENABLE && !message.isNullOrEmpty()) {
            Log.e(tag, message)
        }
    }

    fun w(tag: String?, message: String?) {
        if (LOG_ENABLE && !message.isNullOrEmpty()) {
            Log.w(tag, message)
        }
    }

    fun v(tag: String?, message: String?) {
        if (LOG_ENABLE && !message.isNullOrEmpty()) {
            Log.v(tag, message)
        }
    }

    fun i(tag: Tag, message: String?) {
        if (LOG_ENABLE && !message.isNullOrEmpty()) {
            Log.i(tag.toString(), message)
        }
    }

    @JvmStatic
    fun d(tag: Tag, message: String?) {
        if (LOG_ENABLE && !message.isNullOrEmpty()) {
            Log.d(tag.toString(), message)
        }
    }

    fun e(tag: Tag, message: String?) {
        if (LOG_ENABLE && !message.isNullOrEmpty()) {
            Log.e(tag.toString(), message)
        }
    }

    fun w(tag: Tag, message: String?) {
        if (LOG_ENABLE && !message.isNullOrEmpty()) {
            Log.w(tag.toString(), message)
        }
    }

    fun v(tag: Tag, message: String?) {
        if (LOG_ENABLE && message.isNullOrEmpty() == false) {
            Log.v(tag.toString(), message)
        }
    }

    fun printStackTrace(e: Exception) {
        if (LOG_ENABLE) {
            e.printStackTrace()
        }
    }

    fun makeLogMessage(tag: String?, message: String?): String {
        return String.format(Locale.US, LOG_FORMAT, tag, message)
    }

    fun makeLogMessage(tag: Class<*>?, message: String?): String? {
        return if (tag != null) {
            String.format(
                Locale.US,
                LOG_FORMAT,
                tag.simpleName,
                message
            )
        } else message
    }

    enum class Tag {
        HOLDER,
        STATISTICS,
        BRIDGE,
        LIVE,
        IN_WEB_VIEW,
        ACTIVITY_LIFE_CYCLE,
        APP_CONFIG,
        PUSH,
        WIDGET,
        DEEP_LINK,
        DOMAIN,
        PRESENTATION,
        DOWNLOAD
    }
}

fun AppLogger.Tag.w(message: String?) {
    AppLogger.w(this, message)
}

fun AppLogger.Tag.d(message: String?) {
    AppLogger.d(this, message)
}

fun AppLogger.Tag.i(message: String?) {
    AppLogger.i(this, message)
}

fun AppLogger.Tag.e(message: String?) {
    AppLogger.e(this, message)
}

fun AppLogger.Tag.v(message: String?) {
    AppLogger.v(this, message)
}
package pet.perpet.equal

import android.annotation.SuppressLint
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import pet.perpet.equal.support.logger.AppLogger
import pet.perpet.framework.fragment.ClassUtil
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

internal object GsonLoader {
    val gson: Gson by lazy {
        GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()
    }
}

fun <T> T.toJson(): String {
    return GsonLoader.gson.toJson(this)
}

fun <T> String.fromJson(type: Class<T>): T {
    return GsonLoader.gson.fromJson(this, type)
}

fun <T> String.fromJson(type: TypeToken<T>): T {
    return GsonLoader.gson.fromJson(this, type.type)
}

val gson
    get() = GsonLoader.gson


fun getTypeToken(target: Class<*>, vararg aClass: Class<*>): TypeToken<*> {
    return TypeToken.get(ClassUtil.getInnerClassExtendsType(target, *aClass))
}

fun <T> List<T>.asArrayList(): ArrayList<T>? {
    return if (this is ArrayList<T>) {
        this
    } else {
        null
    }
}

fun Any.asString(): String? {
    return if (this is String) {
        this
    } else {
        null
    }
}


fun Date.asDateString(format: String = "yyyy-MM-dd HH:mm:ss"): String? {
    try {
        return SimpleDateFormat(format).format(this)
    } catch (e: Exception) {
        AppLogger.printStackTrace(e)
    }
    return null
}

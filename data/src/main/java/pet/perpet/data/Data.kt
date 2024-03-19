package pet.perpet.data

import android.content.Context
import com.orhanobut.hawk.Hawk
import okhttp3.Interceptor

fun Long?.nonnull(): Long {
    if (this != null) {
        return this
    }
    return 0
}

fun Boolean?.nonnull(): Boolean {
    if (this != null) {
        return this
    }
    return false
}

fun Int?.nonnull(): Int {
    if (this != null) {
        return this
    }
    return 0
}

fun Int?.nonnull(value: Int): Int {
    if (this != null) {
        return this
    }
    return value
}

fun Float?.nonnull(): Float {
    if (this != null) {
        return this
    }
    return 0f
}

fun Double?.nonnull(): Double {
    if (this != null) {
        return this
    }
    return 0.0
}

fun String?.nonnull(): String {
    if (this != null) {
        return this
    }
    return ""
}

fun Int?.orEmpty(invalid: (value: Int) -> Boolean): String {
    return if (this != null) {
        val result = invalid.invoke(this)
        if (result == true) {
            ""
        } else {
            this.toString()
        }
    } else {
        ""
    }
}

fun <T> List<T>.findIndex(compare: ((value: T) -> Boolean)): Int {
    this.forEachIndexed { index, t ->
        if (compare(t) == true) {
            return index
        }
    }
    return -1
}

fun <K, V> MutableMap<K, V>.validMap(key: K, value: V, defaultInt: Int = 0) {
    fun isUse(value: V): Boolean {
        if (value is Int) {
            return value > defaultInt
        } else if (value is String) {
            return value.isNotEmpty()
        }
        return false
    }
    if (isUse(value)) {
        this[key] = value
    }
}

object DataConfig {

    //======================================================================
    // Variables
    //======================================================================

    private var pro: ApiConfigProvider? = null

    lateinit var factory: ApiConfigProviderFactory

    //======================================================================
    // Public Methods
    //======================================================================

    fun init(context: Context, factory: ApiConfigProviderFactory) {
        Hawk.init(context).build()
        DataConfig.factory = factory
    }

    fun getProvider(): ApiConfigProvider {
        if (pro == null) {
            pro = factory.create()
        }
        return pro as ApiConfigProvider
    }
}

abstract class ApiConfigProviderFactory {

    abstract fun create(): ApiConfigProvider
}

abstract class ApiConfigProvider {

    abstract val clintId: String

    abstract val domain: String

    abstract val accessToken: String?

    abstract val refreshToken: String?

    abstract val userAgent: String

    abstract val logger: Boolean

    abstract val application: Context

    abstract fun createInterceptor(): Interceptor

    open fun logout() {

    }
}
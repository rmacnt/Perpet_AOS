package pet.perpet.equal.support.deeplink.model

import android.net.Uri
import pet.perpet.equal.support.logger.AppLogger
import java.io.UnsupportedEncodingException
import android.os.Parcelable


abstract class BaseArgument (open val uri: Uri) : Parcelable {

    abstract val type: Type

    abstract val scene: String

    abstract val keyword: String

    abstract val petId: String

    abstract val subscriptionId: String

    abstract val contentId: String

    abstract val examinationId: String

    val isHttpUrlMatch: Boolean
        get() = uri.toString().run {
            this.matches(Regex("^(https?://[a-zA-Z0-9./]*)\$"))
        }

    fun log() {
        log("host : ${uri.host}")
        log("path : ${uri.path}")
        log("type : $type")
        log("query : $scene")
    }

    fun log(message: String) {
        AppLogger.w(AppLogger.Tag.DEEP_LINK, "[${this.javaClass.simpleName}] $message")
    }

    @Throws(UnsupportedEncodingException::class)
    fun decodeBase65(txt: String): String {
        return txt
    }

    fun Uri.safetyQueryParameter(key: String): String {
        try {
            return uri.getQueryParameter(key).orEmpty()
        } catch (e: Exception) {
            // Pass
        }
        return ""
    }

    fun Uri.safetyLastQueryParameter(): String {
        try {
            return uri.lastPathSegment.orEmpty()
        } catch (e: Exception) {
            // Pass
        }
        return ""
    }
}
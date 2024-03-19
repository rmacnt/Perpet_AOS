package pet.perpet.equal.support.push.model

import com.google.firebase.messaging.RemoteMessage
import pet.perpet.equal.support.logger.AppLogger
import pet.perpet.framework.nonnull
import java.io.Serializable

data class PushMessage(
    val type: String? = null,
    val title: String? = null,
    val message: String? = null,
    val body: MessageBody? = null,
) : Serializable {

    val toType: Type?
        get() = Type.parse(
            this.type.nonnull())

    //======================================================================
    // companion
    //======================================================================

    companion object {
        @JvmStatic
        fun parse(rm: RemoteMessage): PushMessage {
            try {
                AppLogger.d(AppLogger.Tag.PUSH, "#### parse ####")
                AppLogger.d(AppLogger.Tag.PUSH, "raw data : ${rm.data}")

                val title = rm.notification?.title
                val message = rm.notification?.body

                AppLogger.d(AppLogger.Tag.PUSH, "title : $title")
                AppLogger.d(AppLogger.Tag.PUSH, "message : $message")

                val type = rm.data["type"]
                val link = rm.data["link"]
                val action = rm.data["action"]
                val imageUrl = rm.data["imageUrl"]

                AppLogger.d(AppLogger.Tag.PUSH, "type : $type")
                AppLogger.d(AppLogger.Tag.PUSH, "link : $link")
                AppLogger.d(AppLogger.Tag.PUSH, "action : $action")
                AppLogger.d(AppLogger.Tag.PUSH, "imageUrl : $imageUrl")

                return PushMessage(
                    type,
                    title,
                    message,
                    MessageBody(title , message , link , action , imageUrl.orEmpty())
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return PushMessage()
        }
    }

    //======================================================================
    // Type
    //======================================================================


    enum class Type {
        home,
        search,
        publication,
        examination,
        additionalExamination,
        subscription,
        more,
        orderList,
        intake,
        Invalid,
        examinationResult,
        shippingInfo,
        paymentInfo,
        notifications,
        account,
        nutrientReport,
        AppBrowser,
        ExternalBrowser;

        val isShowNotification: Boolean
            get() {
                return when (this) {
                    Invalid -> {
                        false
                    }

                    else -> true
                }
            }

        fun toNotificationId(): Int {
            return Integer.MAX_VALUE
        }

        companion object {

            fun parseString(type: String): Type {
                try {
                    AppLogger.w(AppLogger.Tag.PUSH, "parse >> type $type")
                    return valueOf(type)
                } catch (e: Exception) {
                    AppLogger.e(AppLogger.Tag.PUSH, "parse >> type $type")
                    e.printStackTrace()
                }
                return Invalid
            }

            fun parse(type: String): Type {
                try {
                    AppLogger.w(AppLogger.Tag.PUSH, "parse >> type $type")
                    return valueOf(type)
                } catch (e: Exception) {
                    AppLogger.e(AppLogger.Tag.PUSH, "parse >> type $type")
                    e.printStackTrace()
                }
                return Invalid
            }
        }

    }
}
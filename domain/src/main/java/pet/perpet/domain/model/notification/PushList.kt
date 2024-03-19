package pet.perpet.domain.model.notification

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import pet.perpet.domain.asDate
import java.text.SimpleDateFormat
import java.util.Date

data class PushList(
    @SerializedName("cn")
    val cn: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("insert_date")
    val insert_date: String?,
    @SerializedName("insert_user")
    val insert_user: Int?,
    @SerializedName("schedule_group")
    val schedule_group: String?,
    @SerializedName("tags")
    val tags: String?,
    @SerializedName("target")
    val target: Int?,
    @SerializedName("time")
    val time: String?,
    @SerializedName("tn")
    val tn: String?,
    @SerializedName("transport_date")
    val transport_date: String?,
    @SerializedName("gbn")
    val gbn: String?,
    @SerializedName("link")
    val link: String?,
    @SerializedName("action")
    val action: String?,
    @SerializedName("checked_yn")
    val checked_yn: String?

) {

    val toType: Type
        get() = Type.parse(action)

    val isValidType: Boolean
        get() {
            return toType != Type.Invalid
        }


    //======================================================================
    // Public Methods
    //======================================================================

    @SuppressLint("SimpleDateFormat")
    fun toDiff(): String {
        val dst = this.transport_date?.asDate()
        if (dst != null) {
            val src = System.currentTimeMillis()

            val formatter = SimpleDateFormat("MM.dd")
            val diff = src - dst.time

            val second = 1000
            val minute = second * 60
            val hour = minute * 60
            val day = hour * 24


            return when {
                diff >= day -> formatter.format(dst)
                diff >= hour -> "${diff / hour}시간 전"
                diff >= minute -> "${diff / minute}분 전"
                diff >= second -> "지금"
                else -> "지금"
            }
        }
        return "지금"
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
        examinationResult,
        shippingInfo,
        paymentInfo,
        notifications,
        account,
        nutrientReport,
        Invalid,
        AppBrowser,
        ExternalBrowser;

        companion object {

            fun parse(type: String?): Type {
                try {
                    return valueOf(type.orEmpty())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                return Invalid
            }
        }
    }
}
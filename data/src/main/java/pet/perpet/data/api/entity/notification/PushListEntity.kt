package pet.perpet.data.api.entity.notification

import com.google.gson.annotations.SerializedName

data class PushListEntity(
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
)
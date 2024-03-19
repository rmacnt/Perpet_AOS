package pet.perpet.data.api.entity.tracker

import com.google.gson.annotations.SerializedName

data class TrackerProgresseEntity(
    @SerializedName("code")
    val code: String?,
    @SerializedName("etc")
    val etc: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("text")
    val text: String?,
    @SerializedName("time")
    val time: String?
)
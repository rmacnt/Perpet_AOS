package pet.perpet.data.api.entity.tracker

import com.google.gson.annotations.SerializedName

data class TrackerCarrierEntity(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("tel")
    val tel: String?
)
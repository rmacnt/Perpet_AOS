package pet.perpet.domain.model.tracker

import com.google.gson.annotations.SerializedName

data class TrackerCarrier(
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("tel")
    val tel: String?
)
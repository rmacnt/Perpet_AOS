package pet.perpet.domain.model.tracker

import com.google.gson.annotations.SerializedName

data class TrackerFrom(
    @SerializedName("name")
    val name: String?,
    @SerializedName("time")
    val time: String?
)
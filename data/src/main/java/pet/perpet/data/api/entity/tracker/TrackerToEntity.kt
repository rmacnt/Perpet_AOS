package pet.perpet.data.api.entity.tracker

import com.google.gson.annotations.SerializedName

data class TrackerToEntity(
    @SerializedName("name")
    val name: String?,
    @SerializedName("time")
    val time: Any?
)
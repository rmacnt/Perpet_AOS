package pet.perpet.domain.model.tracker

import com.google.gson.annotations.SerializedName

data class TrackerTo(
    @SerializedName("name")
    val name: String?,
    @SerializedName("time")
    val time: Any?
)
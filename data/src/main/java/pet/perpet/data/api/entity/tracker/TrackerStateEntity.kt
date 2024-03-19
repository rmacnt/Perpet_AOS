package pet.perpet.data.api.entity.tracker

import com.google.gson.annotations.SerializedName

data class TrackerStateEntity(
    @SerializedName("complete")
    val complete: Boolean?,
    @SerializedName("completeYN")
    val completeYN: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("kind")
    val kind: String?,
    @SerializedName("text")
    val text: String?
)
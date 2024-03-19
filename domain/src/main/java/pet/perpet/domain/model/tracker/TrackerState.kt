package pet.perpet.domain.model.tracker

import com.google.gson.annotations.SerializedName

data class TrackerState(
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
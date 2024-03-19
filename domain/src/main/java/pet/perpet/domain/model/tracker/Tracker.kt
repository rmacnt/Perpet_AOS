package pet.perpet.domain.model.tracker

import com.google.gson.annotations.SerializedName

data class Tracker(
    @SerializedName("carrier")
    val carrier: TrackerCarrier?,
    @SerializedName("from")
    val from: TrackerFrom?,
    @SerializedName("progresses")
    val progresses: List<TrackerProgresse>?,
    @SerializedName("state")
    val state: TrackerState?,
    @SerializedName("to")
    val to: TrackerTo?
)
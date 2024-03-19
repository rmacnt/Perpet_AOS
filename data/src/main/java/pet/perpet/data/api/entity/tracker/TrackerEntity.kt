package pet.perpet.data.api.entity.tracker

import com.google.gson.annotations.SerializedName

data class TrackerEntity(
    @SerializedName("carrier")
    val carrier: TrackerCarrierEntity?,
    @SerializedName("from")
    val from: TrackerFromEntity?,
    @SerializedName("progresses")
    val progresses: List<TrackerProgresseEntity>?,
    @SerializedName("state")
    val state: TrackerStateEntity?,
    @SerializedName("to")
    val to: TrackerToEntity?
)
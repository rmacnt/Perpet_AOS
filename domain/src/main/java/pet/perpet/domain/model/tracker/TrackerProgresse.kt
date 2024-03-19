package pet.perpet.domain.model.tracker

import com.google.gson.annotations.SerializedName
import pet.perpet.data.nonnull

data class TrackerProgresse(
    @SerializedName("code")
    val code: String?,
    @SerializedName("etc")
    val etc: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("text")
    val text: String?,
    @SerializedName("time")
    var time: String?,
    var rootType: Boolean = false
) : Comparable<TrackerProgresse> {

    override fun compareTo(other: TrackerProgresse): Int {
       return other.time?.compareTo(time.nonnull()).nonnull()
    }
}
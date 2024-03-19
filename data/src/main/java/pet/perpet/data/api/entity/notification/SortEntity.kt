package pet.perpet.data.api.entity.notification

import com.google.gson.annotations.SerializedName

data class SortEntity(
    @SerializedName("empty")
    val empty: Boolean?,
    @SerializedName("sorted")
    val sorted: Boolean?,
    @SerializedName("unsorted")
    val unsorted: Boolean?
)
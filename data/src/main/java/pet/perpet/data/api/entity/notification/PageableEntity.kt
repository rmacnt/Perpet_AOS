package pet.perpet.data.api.entity.notification

import com.google.gson.annotations.SerializedName

data class PageableEntity(
    @SerializedName("offset")
    val offset: Int?,
    @SerializedName("pageNumber")
    val pageNumber: Int?,
    @SerializedName("pageSize")
    val pageSize: Int?,
    @SerializedName("paged")
    val paged: Boolean?,
    @SerializedName("sort")
    val sort: SortEntity?,
    @SerializedName("unpaged")
    val unpaged: Boolean?
)
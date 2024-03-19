package pet.perpet.data.api.entity.bookmark

import com.google.gson.annotations.SerializedName

data class BookmarkContentEntity(
    @SerializedName("images")
    val images: List<String>?,
    @SerializedName("sub_title")
    val sub_title: String?,
    @SerializedName("text")
    val text: String?,
    @SerializedName("videos")
    val videos: List<String>?
)
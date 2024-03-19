package pet.perpet.data.api.entity.search

import com.google.gson.annotations.SerializedName

data class ContentEntity(
    @SerializedName("images")
    val images: List<String>?,
    @SerializedName("sub_title")
    val sub_title: String?,
    @SerializedName("text")
    val text: String?,
    @SerializedName("videos")
    val videos: List<String>?
)
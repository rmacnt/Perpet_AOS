package pet.perpet.domain.model.search

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Content(
    @SerializedName("images")
    val images: List<String>?,
    @SerializedName("sub_title")
    val sub_title: String?,
    @SerializedName("text")
    val text: String?,
    @SerializedName("videos")
    val videos: List<String>?
): Serializable
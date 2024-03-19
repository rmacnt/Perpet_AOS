package pet.perpet.domain.model.main

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MainCardContent (
    @SerializedName("images")
    val images: ArrayList<Any>?,
    @SerializedName("sub_title")
    val sub_title: String?,
    @SerializedName("text")
    val text: String?,
    @SerializedName("videos")
    val videos: ArrayList<Any>?
): Serializable
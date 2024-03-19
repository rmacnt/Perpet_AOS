package pet.perpet.data.api.entity.main

import com.google.gson.annotations.SerializedName

data class MainCardContentEntity (
    @SerializedName("images")
    val images: ArrayList<Any>?,
    @SerializedName("sub_title")
    val sub_title: String?,
    @SerializedName("text")
    val text: String?,
    @SerializedName("videos")
    val videos: ArrayList<Any>?
)
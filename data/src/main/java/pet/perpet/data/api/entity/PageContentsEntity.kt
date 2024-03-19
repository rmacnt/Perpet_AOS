package pet.perpet.data.api.entity

import com.google.gson.annotations.SerializedName

data class PageContentsEntity<T>(
    @SerializedName("content")
    val data: ArrayList<T>?
)

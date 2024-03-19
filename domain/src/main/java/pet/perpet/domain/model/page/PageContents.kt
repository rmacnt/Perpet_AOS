package pet.perpet.domain.model.page

import com.google.gson.annotations.SerializedName

data class PageContents<T>(
    @SerializedName("content")
    val content: ArrayList<T>?
)
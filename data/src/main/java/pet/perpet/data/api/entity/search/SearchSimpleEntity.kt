package pet.perpet.data.api.entity.search

import com.google.gson.annotations.SerializedName

data class SearchSimpleEntity(
    @SerializedName("contents_id")
    val contents_id: Int,
    @SerializedName("search")
    val search: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("type")
    val type: String
)
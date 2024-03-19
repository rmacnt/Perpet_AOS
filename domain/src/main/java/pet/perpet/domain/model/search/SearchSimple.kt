package pet.perpet.domain.model.search

import com.google.gson.annotations.SerializedName

data class SearchSimple(
    @SerializedName("contents_id")
    val contents_id: Int,
    @SerializedName("search")
    val search: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("type")
    val type: String
)
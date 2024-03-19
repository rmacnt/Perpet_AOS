package pet.perpet.equal.presentation.search.model

import com.google.gson.annotations.SerializedName

data class SearchKeyword (
    @SerializedName("keyword")
    val keyword: String?
)
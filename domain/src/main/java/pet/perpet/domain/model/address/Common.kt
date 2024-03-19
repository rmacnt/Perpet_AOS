package pet.perpet.domain.model.address

import com.google.gson.annotations.SerializedName

data class Common(
    @SerializedName("countPerPage")
    val countPerPage: String,
    @SerializedName("currentPage")
    val currentPage: String,
    @SerializedName("errorCode")
    val errorCode: String,
    @SerializedName("errorMessage")
    val errorMessage: String,
    @SerializedName("totalCount")
    val totalCount: String
)
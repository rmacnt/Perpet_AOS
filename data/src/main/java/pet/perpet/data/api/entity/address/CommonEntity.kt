package pet.perpet.data.api.entity.address

import com.google.gson.annotations.SerializedName

data class CommonEntity(
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
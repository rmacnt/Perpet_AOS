package pet.perpet.domain.model.search

import com.google.gson.annotations.SerializedName

data class SearchTag (
    @SerializedName("id")
    val id: Int?,
    @SerializedName("insert_user")
    val insert_user: Int?,
    @SerializedName("insert_date")
    val insert_date: String?,
    @SerializedName("update_date")
    val update_date: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("used_count")
    val used_count: Int?,
    @SerializedName("health_yn")
    val health_yn: String?,
    @SerializedName("medical_yn")
    val medical_yn: String?
)
package pet.perpet.data.api.entity.medical

import com.google.gson.annotations.SerializedName

data class TagEntity(
    @SerializedName("health_yn")
    val health_yn: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("insert_date")
    val insert_date: String,
    @SerializedName("insert_user")
    val insert_user: Int,
    @SerializedName("medical_yn")
    val medical_yn: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("update_date")
    val update_date: String,
    @SerializedName("used_count")
    val used_count: Int
)
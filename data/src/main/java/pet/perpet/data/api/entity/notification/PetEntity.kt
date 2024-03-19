package pet.perpet.data.api.entity.notification

import com.google.gson.annotations.SerializedName

data class PetEntity(
    @SerializedName("age")
    val age: Any?,
    @SerializedName("breed")
    val breed: Any?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("main_yn")
    val main_yn: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("neutralization")
    val neutralization: Any?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("user_id")
    val user_id: Int?,
    @SerializedName("weight")
    val weight: String?
)
package pet.perpet.data.api.entity.notification

import com.google.gson.annotations.SerializedName

data class TagEntity (
    @SerializedName("health_yn")
    val health_yn: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("medical_yn")
    val medical_yn: String?,
    @SerializedName("name")
    val name: String?
)
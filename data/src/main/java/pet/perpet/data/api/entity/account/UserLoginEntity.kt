package pet.perpet.data.api.entity.account

import com.google.gson.annotations.SerializedName

data class UserLoginEntity (

    @SerializedName("accessToken")
    val accessToken: String?,
    @SerializedName("refreshToken")
    val refreshToken: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("tokenType")
    val tokenType: String?,
    @SerializedName("signUp")
    val signUp: Boolean?
)
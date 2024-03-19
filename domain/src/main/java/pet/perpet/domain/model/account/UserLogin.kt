package pet.perpet.domain.model.account

import com.google.gson.annotations.SerializedName

data class UserLogin(

    @SerializedName("accessToken")
    val accessToken: String?,
    @SerializedName("refreshToken")
    val refreshToken: String?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("tokenType")
    val tokenType: String?,
    @SerializedName("signUp")
    val signUp: Boolean?
)
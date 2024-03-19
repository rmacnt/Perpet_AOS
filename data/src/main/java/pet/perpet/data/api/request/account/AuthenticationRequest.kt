package pet.perpet.data.api.request.account

import com.google.gson.annotations.SerializedName

data class AuthenticationRequest (
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("auth_num")
    val auth_num: String? = null,
)
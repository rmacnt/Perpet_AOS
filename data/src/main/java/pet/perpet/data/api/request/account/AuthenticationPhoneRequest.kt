package pet.perpet.data.api.request.account

import com.google.gson.annotations.SerializedName

data class AuthenticationPhoneRequest (
    @SerializedName("phone")
    val phone: String?
)
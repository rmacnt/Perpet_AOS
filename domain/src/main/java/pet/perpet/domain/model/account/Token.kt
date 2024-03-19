package pet.perpet.domain.model.account

import com.google.gson.annotations.SerializedName
import java.util.*

data class Token(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("accessToken")
    var accessToken: String?,

    @SerializedName("refreshToken")
    var refreshToken: String,

    @SerializedName("tokenType")
    var tokenType: String?,

    var expireDate: Date? = null
)
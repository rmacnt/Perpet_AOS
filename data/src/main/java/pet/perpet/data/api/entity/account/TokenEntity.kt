package pet.perpet.data.api.entity.account

import com.google.gson.annotations.SerializedName
import pet.perpet.data.repository.base.datasource.AccessTokenEntity
import java.util.*

@AccessTokenEntity
data class TokenEntity(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("accessToken")
    var accessToken: String?,

    @SerializedName("refreshToken")
    var refreshToken: String?,

    @SerializedName("tokenType")
    var tokenType: String?,

    var expireDate: Date? = null
)

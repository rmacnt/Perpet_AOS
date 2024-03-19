package pet.perpet.data.api.request.account

import com.google.gson.annotations.SerializedName

data class AccountRequest (
    @SerializedName("id")
    val id: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("device_unique")
    val device_unique: String?,
    @SerializedName("device_group")
    val device_group: String?,
    @SerializedName("device_num")
    val device_num: String?,
    @SerializedName("service_agree")
    val service_agree: String?,
    @SerializedName("privacy_agree")
    val privacy_agree: String?,
    @SerializedName("marketing_agree")
    val marketing_agree: String?,
)
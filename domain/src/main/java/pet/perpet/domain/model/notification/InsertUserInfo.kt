package pet.perpet.domain.model.notification

import com.google.gson.annotations.SerializedName

data class InsertUserInfo (
    @SerializedName("address")
    val address: String?,
    @SerializedName("address_detail")
    val address_detail: String?,
    @SerializedName("birth")
    val birth: String?,
    @SerializedName("carrier")
    val carrier: String?,
    @SerializedName("cert_agree")
    val cert_agree: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("emailVerifiedYn")
    val emailVerifiedYn: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("marketing_agree")
    val marketing_agree: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("privacy_agree")
    val privacy_agree: String?,
    @SerializedName("profileImageUrl")
    val profileImageUrl: String?,
    @SerializedName("providerId")
    val providerId: String?,
    @SerializedName("service_agree")
    val service_agree: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("uid")
    val uid: String?,
    @SerializedName("use_yn")
    val use_yn: String?
)
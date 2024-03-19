package pet.perpet.data.api.entity.account

import com.google.gson.annotations.SerializedName

data class UserEntity(
    @SerializedName("address")
    val address: Any?,
    @SerializedName("address_detail")
    val address_detail: Any?,
    @SerializedName("age_14_agree")
    val age_14_agree: Any?,
    @SerializedName("birth")
    val birth: Any?,
    @SerializedName("carrier")
    val carrier: Any?,
    @SerializedName("cert_agree")
    val cert_agree: String?,
    @SerializedName("e_commerce_agree")
    val e_commerce_agree: Any?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("emailVerifiedYn")
    val emailVerifiedYn: String?,
    @SerializedName("gender")
    val gender: Any?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("insert_date")
    val insert_date: String?,
    @SerializedName("insert_user")
    val insert_user: Any?,
    @SerializedName("marketing_agree")
    val marketing_agree: String?,
    @SerializedName("marketing_agree_date")
    val marketing_agree_date: Any?,
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
    @SerializedName("providerType")
    val providerType: String?,
    @SerializedName("pw")
    val pw: String?,
    @SerializedName("roleType")
    val roleType: String?,
    @SerializedName("service_agree")
    val service_agree: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("uid")
    val uid: Any?,
    @SerializedName("update_date")
    val update_date: String?,
    @SerializedName("update_user")
    val update_user: Int?,
    @SerializedName("use_yn")
    val use_yn: String?,
    @SerializedName("authentication_yn")
    val authentication_yn: String?,
    @SerializedName("noti_event")
    val noti_event: String?,
    @SerializedName("noti_info")
    val noti_info: String?

)

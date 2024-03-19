package pet.perpet.data.api.entity.order

import com.google.gson.annotations.SerializedName

data class OrderAddressEntity(
    @SerializedName("address")
    val address: String?,
    @SerializedName("address_detail")
    val address_detail: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("insert_date")
    val insert_date: String?,
    @SerializedName("main_yn")
    val main_yn: String?,
    @SerializedName("nickname")
    val nickname: String?,
    @SerializedName("ordersInfo")
    val ordersInfo: Any?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("recipient")
    val recipient: String?,
    @SerializedName("update_date")
    val update_date: String?,
    @SerializedName("user_id")
    val user_id: Int?,
    @SerializedName("zipcode")
    val zipcode: String?
)
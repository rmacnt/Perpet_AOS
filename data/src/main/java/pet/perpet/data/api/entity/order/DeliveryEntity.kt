package pet.perpet.data.api.entity.order

import com.google.gson.annotations.SerializedName

data class DeliveryEntity(
    @SerializedName("addr")
    val addr: String?,
    @SerializedName("carrier")
    val carrier: String?,
    @SerializedName("complete_yn")
    val complete_yn: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("insert_date")
    val insert_date: String?,
    @SerializedName("insert_user")
    val insert_user: Int?,
    @SerializedName("order_id")
    val order_id: Int?,
    @SerializedName("trackerid")
    val trackerid: String?,
    @SerializedName("update_date")
    val update_date: String?,
    @SerializedName("update_user")
    val update_user: Int?,
    @SerializedName("user_id")
    val user_id: Int?
)
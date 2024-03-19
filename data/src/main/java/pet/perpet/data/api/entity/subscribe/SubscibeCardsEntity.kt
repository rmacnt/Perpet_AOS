package pet.perpet.data.api.entity.subscribe

import com.google.gson.annotations.SerializedName

data class SubscibeCardsEntity(
    @SerializedName("card_name")
    val card_name: String?,
    @SerializedName("card_nickname")
    val card_nickname: String?,
    @SerializedName("card_number")
    val card_number: String?,
    @SerializedName("customer_uid")
    val customer_uid: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("insert_date")
    val insert_date: String?,
    @SerializedName("ordersInfo")
    val ordersInfo: Any?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("update_date")
    val update_date: String?,
    @SerializedName("user_id")
    val user_id: Int?
)
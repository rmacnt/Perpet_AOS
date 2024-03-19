package pet.perpet.data.api.entity.card

import com.google.gson.annotations.SerializedName

data class CardListEntity(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("user_id")
    val user_id: Int?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("card_nickname")
    val card_nickname: String?,
    @SerializedName("card_number")
    val card_number: String?,
    @SerializedName("card_name")
    val card_name: String?,
    @SerializedName("insert_date")
    val insert_date: String?,
    @SerializedName("update_date")
    val update_date: String?,
    @SerializedName("ordersInfo")
    val ordersInfo: ArrayList<OrdersInfoEntity>?,
)
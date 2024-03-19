package pet.perpet.domain.model.card

import com.google.gson.annotations.SerializedName

data class CardInsert (
    @SerializedName("id")
    val id: Int?,
    @SerializedName("user_id")
    val user_id: Int?,
    @SerializedName("card_nickname")
    val card_nickname: String?,
    @SerializedName("customer_uid")
    val customer_uid: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("insert_user")
    val insert_user: Int?,
    @SerializedName("update_user")
    val update_user: Int?,
    @SerializedName("insert_date")
    val insert_date: String?,
    @SerializedName("update_date")
    val update_date: String?
)
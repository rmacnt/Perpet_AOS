package pet.perpet.data.api.entity.card

import com.google.gson.annotations.SerializedName

data class CardChangeEntity (
    @SerializedName("addr")
    val addr: String?,
    @SerializedName("amount")
    val amount: Double?,
    @SerializedName("cancel_admin_yn")
    val cancel_admin_yn: Any?,
    @SerializedName("cancel_date")
    val cancel_date: Any?,
    @SerializedName("card_id")
    val card_id: Int?,
    @SerializedName("customer_uid")
    val customer_uid: Any?,
    @SerializedName("delivery_day")
    val delivery_day: Int?,
    @SerializedName("expiry_date")
    val expiry_date: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("insert_date")
    val insert_date: String?,
    @SerializedName("next_delivery_date")
    val next_delivery_date: Any?,
    @SerializedName("next_pay_date")
    val next_pay_date: String?,
    @SerializedName("order_number")
    val order_number: String?,
    @SerializedName("package_id")
    val package_id: Any?,
    @SerializedName("pay_day")
    val pay_day: Int?,
    @SerializedName("period")
    val period: String?,
    @SerializedName("pet_id")
    val pet_id: Int?,
    @SerializedName("pg")
    val pg: String?,
    @SerializedName("update_date")
    val update_date: String?,
    @SerializedName("use_yn")
    val use_yn: String?,
    @SerializedName("user_id")
    val user_id: Int?
)
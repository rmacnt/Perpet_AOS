package pet.perpet.data.api.entity.subscribe

import com.google.gson.annotations.SerializedName

data class SubscribeEntity(
    @SerializedName("address")
    val address: SubscribeAddressEntity?,
    @SerializedName("address_id")
    val address_id: Int?,
    @SerializedName("amount")
    val amount: Double?,
    @SerializedName("cancel_admin_yn")
    val cancel_admin_yn: Any?,
    @SerializedName("cancel_date")
    val cancel_date: Any?,
    @SerializedName("card_id")
    val card_id: Int?,
    @SerializedName("card_nickName")
    val card_nickName: Any?,
    @SerializedName("cards")
    val cards: SubscibeCardsEntity?,
    @SerializedName("delivery_day")
    val delivery_day: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("insert_date")
    val insert_date: String?,
    @SerializedName("insert_user")
    val insert_user: Int?,
    @SerializedName("medical_id")
    val medical_id: Int?,
    @SerializedName("next_delivery_date")
    val next_delivery_date: String?,
    @SerializedName("next_pay_date")
    val next_pay_date: String?,
    @SerializedName("order_number")
    val order_number: String?,
    @SerializedName("package_id")
    val package_id: Any?,
    @SerializedName("paid_yn")
    val paid_yn: String?,
    @SerializedName("pay_day")
    val pay_day: Int?,
    @SerializedName("pay_method")
    val pay_method: String?,
    @SerializedName("pet_id")
    val pet_id: Int?,
    @SerializedName("pet_name")
    val pet_name: Any?,
    @SerializedName("pg")
    val pg: String?,
    @SerializedName("products")
    val products: SubscibeProductsEntity?,
    @SerializedName("update_date")
    val update_date: String?,
    @SerializedName("update_user")
    val update_user: Int?,
    @SerializedName("use_yn")
    val use_yn: String?,
    @SerializedName("user_id")
    val user_id: Int?,
    @SerializedName("user_name")
    val user_name: Any?
)
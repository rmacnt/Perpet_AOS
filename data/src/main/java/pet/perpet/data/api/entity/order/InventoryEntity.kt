package pet.perpet.data.api.entity.order

import com.google.gson.annotations.SerializedName

data class InventoryEntity(
    @SerializedName("amount")
    val amount: Double?,
    @SerializedName("delivery_id")
    val delivery_id: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("insert_date")
    val insert_date: Any?,
    @SerializedName("insert_user")
    val insert_user: Any?,
    @SerializedName("order_id")
    val order_id: Int?,
    @SerializedName("pay_method")
    val pay_method: String?,
    @SerializedName("payment_dt")
    val payment_dt: String?,
    @SerializedName("pet_id")
    val pet_id: Int?,
    @SerializedName("pet_name")
    val pet_name: String?,
    @SerializedName("update_date")
    val update_date: Any?,
    @SerializedName("update_user")
    val update_user: Any?,
    @SerializedName("user_id")
    val user_id: Int?,
    @SerializedName("address")
    val address: OrderAddressEntity?,
    @SerializedName("cards")
    val cards: OrderCardsEntity?,
    @SerializedName("delivery")
    val delivery: DeliveryEntity?,
    @SerializedName("medical_id")
    val medical_id: Int?,
    @SerializedName("order_number")
    val order_number: String?,
    @SerializedName("pay_id")
    val pay_id: Any?,
    @SerializedName("products")
    val products: OrderAddProductEntity?,
    @SerializedName("shipping_fee")
    val shipping_fee: Double?,
    @SerializedName("status")
    val status: String?

)
package pet.perpet.data.api.entity.order

import com.google.gson.annotations.SerializedName

data class OrderAddProductEntity(
    @SerializedName("name")
    val name: String?,
    @SerializedName("price")
    val price: Double?,
    @SerializedName("product_id")
    val product_id: Int?,
    @SerializedName("quantity")
    val quantity: Int?,
    @SerializedName("totalPrice")
    val totalPrice: Double?
)
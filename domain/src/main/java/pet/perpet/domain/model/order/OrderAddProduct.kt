package pet.perpet.domain.model.order

import com.google.gson.annotations.SerializedName

data class OrderAddProduct(
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
package pet.perpet.data.api.entity.order

import com.google.gson.annotations.SerializedName

data class OrderProductsEntity(
    @SerializedName("addProducts")
    val addProducts: List<OrderAddProductEntity>?,
    @SerializedName("medicalProducts")
    val medicalProducts: List<OrderMedicalProductEntity>?,
    @SerializedName("totalPrice")
    val totalPrice: Double?
)
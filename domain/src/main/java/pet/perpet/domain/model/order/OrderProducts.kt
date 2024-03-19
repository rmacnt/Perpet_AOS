package pet.perpet.domain.model.order

import com.google.gson.annotations.SerializedName

data class OrderProducts(
    @SerializedName("addProducts")
    val addProducts: List<OrderAddProduct>?,
    @SerializedName("medicalProducts")
    val medicalProducts: List<MedicalProduct>?,
    @SerializedName("totalPrice")
    val totalPrice: Double?
)
package pet.perpet.data.api.entity.subscribe

import com.google.gson.annotations.SerializedName

data class SubscibeProductsEntity(
    @SerializedName("addProducts")
    val addProducts: Any?,
    @SerializedName("medicalProducts")
    val medicalProducts: ArrayList<SubscibeMedicalProductEntity>?,
    @SerializedName("totalPrice")
    val totalPrice: Double?
)
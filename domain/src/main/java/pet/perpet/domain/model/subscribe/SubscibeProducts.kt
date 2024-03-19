package pet.perpet.domain.model.subscribe

import com.google.gson.annotations.SerializedName
import pet.perpet.domain.model.subscribe.SubscibeMedicalProduct

data class SubscibeProducts(
    @SerializedName("addProducts")
    val addProducts: Any?,
    @SerializedName("medicalProducts")
    val medicalProducts: ArrayList<SubscibeMedicalProduct>?,
    @SerializedName("totalPrice")
    val totalPrice: Double?
)
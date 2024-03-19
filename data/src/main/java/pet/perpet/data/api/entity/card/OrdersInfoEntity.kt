package pet.perpet.data.api.entity.card

import com.google.gson.annotations.SerializedName

data class OrdersInfoEntity(
    @SerializedName("order_id")
    val order_id: Int?,
    @SerializedName("pet_id")
    val pet_id: Int?,
    @SerializedName("pet_name")
    val pet_name: String?
)
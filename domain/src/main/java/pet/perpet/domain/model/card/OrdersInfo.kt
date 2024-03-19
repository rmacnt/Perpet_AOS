package pet.perpet.domain.model.card

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OrdersInfo(
    @SerializedName("order_id")
    val order_id: Int?,
    @SerializedName("pet_id")
    val pet_id: Int?,
    @SerializedName("pet_name")
    val pet_name: String?
): Serializable
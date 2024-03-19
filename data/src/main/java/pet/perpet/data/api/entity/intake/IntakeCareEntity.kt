package pet.perpet.data.api.entity.intake

import com.google.gson.annotations.SerializedName

data class IntakeCareEntity(
    @SerializedName("care_id")
    val care_id: Int?,
    @SerializedName("check")
    val check: Boolean?,
    @SerializedName("order_id")
    val order_id: Int?,
    @SerializedName("pet_id")
    val pet_id: Int?,
    @SerializedName("text")
    val text: String?
)
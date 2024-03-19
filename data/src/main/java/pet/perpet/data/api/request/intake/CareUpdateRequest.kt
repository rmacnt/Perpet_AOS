package pet.perpet.data.api.request.intake

import com.google.gson.annotations.SerializedName

data class CareUpdateRequest(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("pet_id")
    val pet_id: Int?,
    @SerializedName("order_id")
    val order_id: Int?,
    @SerializedName("result")
    val result: String?, //O:체크완료, X:미완료
    @SerializedName("date")
    val date: String? // yyyy-MM-dd
)
package pet.perpet.data.api.entity.intake

import com.google.gson.annotations.SerializedName

data class IntakeDataEntity(
    @SerializedName("allCheck")
    val allCheck: Boolean?,
    @SerializedName("cares")
    val cares: List<IntakeCareEntity>?,
    @SerializedName("checkCnt")
    val checkCnt: Int?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("totalCnt")
    val totalCnt: Int?
)
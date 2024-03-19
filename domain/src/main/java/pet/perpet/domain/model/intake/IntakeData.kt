
import com.google.gson.annotations.SerializedName
import pet.perpet.domain.model.intake.IntakeCare

data class IntakeData(
    @SerializedName("allCheck")
    val allCheck: Boolean?,
    @SerializedName("cares")
    val cares: List<IntakeCare>?,
    @SerializedName("checkCnt")
    val checkCnt: Int?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("totalCnt")
    val totalCnt: Int?
)
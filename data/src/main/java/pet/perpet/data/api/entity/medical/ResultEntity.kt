package pet.perpet.data.api.entity.medical

import com.google.gson.annotations.SerializedName

data class ResultEntity(
    @SerializedName("attentionDisease")
    val attentionDisease: String?,
    @SerializedName("avg")
    val avg: Double?,
    @SerializedName("diagnosis")
    val diagnosis: DiagnosisEntity?,
    @SerializedName("healthInfo")
    val healthInfo: String?,
    @SerializedName("nutritionalNeeds")
    val nutritionalNeeds: String?,
    @SerializedName("score")
    val score: Double?
)
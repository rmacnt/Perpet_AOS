package pet.perpet.domain.model.medical

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Result(
    @SerializedName("attentionDisease")
    val attentionDisease: String?,
    @SerializedName("avg")
    val avg: Float?,
    @SerializedName("diagnosis")
    val diagnosis: Diagnosis?,
    @SerializedName("healthInfo")
    val healthInfo: String?,
    @SerializedName("healthInfoTitle")
    val healthInfoTitle: String?,
    @SerializedName("nutritionalNeeds")
    val nutritionalNeeds: String?,
    @SerializedName("score")
    val score: Float?
): Serializable
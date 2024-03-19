package pet.perpet.domain.model.medical

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Diagnosis(
    @SerializedName("diagnosis_id")
    val diagnosis_id: String?,
    @SerializedName("name_eng")
    val name_eng: String?,
    @SerializedName("name_kor")
    val name_kor: String?
): Serializable
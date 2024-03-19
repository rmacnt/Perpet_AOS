package pet.perpet.data.api.entity.medical

import com.google.gson.annotations.SerializedName

data class DiagnosisEntity(
    @SerializedName("diagnosis_id")
    val diagnosis_id: String?,
    @SerializedName("name_eng")
    val name_eng: String?,
    @SerializedName("name_kor")
    val name_kor: String?
)
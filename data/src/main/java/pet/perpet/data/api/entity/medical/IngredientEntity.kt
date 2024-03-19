package pet.perpet.data.api.entity.medical

import com.google.gson.annotations.SerializedName

data class IngredientEntity(
    @SerializedName("compositionType")
    val compositionType: String?,
    @SerializedName("description")
    val description: Any?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("percent")
    val percent: Double?,
    @SerializedName("caution")
    val caution: String?,
    @SerializedName("effect")
    val effect: String?,
    @SerializedName("dosage")
    val dosage: Double?
)
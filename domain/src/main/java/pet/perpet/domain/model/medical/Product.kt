package pet.perpet.domain.model.medical

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Product(
    @SerializedName("code_index")
    val code_index: Int?,
    @SerializedName("count_per_unit")
    val count_per_unit: Int?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("ingredient")
    val ingredient: ArrayList<Ingredient>?,
    @SerializedName("kg_per_unit")
    val kg_per_unit: Int?,
    @SerializedName("name_eng")
    val name_eng: String?,
    @SerializedName("name_kor")
    val name_kor: String?,
    @SerializedName("price_per_count")
    val price_per_count: Double?,
    @SerializedName("profit_per_count")
    val profit_per_count: Int?,
    @SerializedName("summary")
    val summary: String?,
    @SerializedName("supplement_id")
    val supplement_id: String?,
    @SerializedName("supplement_type")
    val supplement_type: String?,
    @SerializedName("tags")
    val tags: ArrayList<Tag>?,
    @SerializedName("caution")
    val caution: String?,
    @SerializedName("storage_precautions")
    val storage_precautions: String?
): Serializable
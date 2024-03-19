package pet.perpet.data.api.entity.search

import com.google.gson.annotations.SerializedName

data class SearchProductEntity(
    @SerializedName("category_id")
    val category_id: Int?,
    @SerializedName("count_per_unit")
    val count_per_unit: Int?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("ingredient")
    val ingredient: List<IngredientEntity>?,
    @SerializedName("insert_date")
    val insert_date: String?,
    @SerializedName("insert_user")
    val insert_user: Any?,
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
    @SerializedName("tag_id")
    val tag_id: String?,
    @SerializedName("tags")
    val tags: List<TagEntity>?,
    @SerializedName("update_date")
    val update_date: String?,
    @SerializedName("update_user")
    val update_user: Int?,
    @SerializedName("use_yn")
    val use_yn: String?,
    @SerializedName("caution")
    val caution: String?,
    @SerializedName("storage_precautions")
    val storage_precautions: String?
)
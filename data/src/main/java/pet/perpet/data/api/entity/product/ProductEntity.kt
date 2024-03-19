package pet.perpet.data.api.entity.product

import com.google.gson.annotations.SerializedName

data class ProductEntity(
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
    val ingredient: List<ProductIngredientEntity>?,
    @SerializedName("insert_date")
    val insert_date: String?,
    @SerializedName("insert_user")
    val insert_user: Int?,
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
    val summary: Any?,
    @SerializedName("supplement_id")
    val supplement_id: String?,
    @SerializedName("supplement_type")
    val supplement_type: String?,
    @SerializedName("tag_id")
    val tag_id: String?,
    @SerializedName("tags")
    val tags: List<ProductTagEntity>?,
    @SerializedName("update_date")
    val update_date: String?,
    @SerializedName("update_user")
    val update_user: Int?,
    @SerializedName("use_yn")
    val use_yn: String?

)
package pet.perpet.data.api.entity.search

import com.google.gson.annotations.SerializedName

data class IngredientEntity(
    @SerializedName("caution")
    val caution: String?,
    @SerializedName("compositionType")
    val compositionType: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("effect")
    val effect: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("insert_date")
    val insert_date: String?,
    @SerializedName("insert_user")
    val insert_user: Any?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("percent")
    val percent: Double?,
    @SerializedName("tag_id")
    val tag_id: String?,
    @SerializedName("tags")
    val tags: List<TagEntity>?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("update_date")
    val update_date: String?,
    @SerializedName("update_user")
    val update_user: Any?,
    @SerializedName("use_yn")
    val use_yn: String?,
    @SerializedName("dosage")
    val dosage: Double?
)
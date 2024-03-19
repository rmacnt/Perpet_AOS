package pet.perpet.data.api.entity.product

import com.google.gson.annotations.SerializedName

data class ProductIngredientEntity(
    @SerializedName("caution")
    val caution: Any?,
    @SerializedName("compositionType")
    val compositionType: String?,
    @SerializedName("description")
    val description: Any?,
    @SerializedName("effect")
    val effect: Any?,
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
    val tags: ArrayList<ProductTagEntity>?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("update_date")
    val update_date: String?,
    @SerializedName("update_user")
    val update_user: Any?,
    @SerializedName("use_yn")
    val use_yn: String?
)
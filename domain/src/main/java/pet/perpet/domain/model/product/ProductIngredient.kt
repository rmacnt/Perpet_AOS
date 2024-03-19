package pet.perpet.domain.model.product

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import pet.perpet.domain.model.product.ProductTag
import java.io.Serializable

@Parcelize
data class ProductIngredient(
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
    val insert_user: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("percent")
    val percent: Double?,
    @SerializedName("tag_id")
    val tag_id: String?,
    @SerializedName("tags")
    val tags: ArrayList<ProductTag>?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("update_date")
    val update_date: String?,
    @SerializedName("update_user")
    val update_user: Int?,
    @SerializedName("use_yn")
    val use_yn: String?
) : Parcelable
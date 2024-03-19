package pet.perpet.domain.model.search

import com.google.gson.annotations.SerializedName
import pet.perpet.data.nonnull
import java.io.Serializable

data class Ingredient(
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
    val tags: List<Tag>?,
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

): Serializable , Comparable<Ingredient> {

    override fun compareTo(other: Ingredient): Int {

        if (other.dosage.nonnull() < dosage.nonnull()) {
            return 1
        } else if (other.dosage.nonnull() > dosage.nonnull()) {
            return -1
        }
        return 0
    }
}
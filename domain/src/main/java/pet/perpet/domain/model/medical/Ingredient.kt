package pet.perpet.domain.model.medical

import com.google.gson.annotations.SerializedName
import pet.perpet.data.nonnull
import java.io.Serializable

data class Ingredient(
    @SerializedName("compositionType")
    val compositionType: String?,
    @SerializedName("description")
    val description: String?,
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
package pet.perpet.domain.model.product

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Parcelize
data class ProductTag(
    @SerializedName("health_yn")
    val health_yn: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("medical_yn")
    val medical_yn: String?,
    @SerializedName("name")
    val name: String?
) : Parcelable
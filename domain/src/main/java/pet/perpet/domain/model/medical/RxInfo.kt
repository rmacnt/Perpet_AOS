package pet.perpet.domain.model.medical

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RxInfo (
    @SerializedName("dosage")
    val dosage: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("daily_dosage_count")
    val daily_dosage_count: Int?,
    @SerializedName("daily_dosage_mg")
    val daily_dosage_mg: Int?,
    @SerializedName("daily_price")
    val daily_price: Int?,



): Serializable
package pet.perpet.data.api.entity.medical

import com.google.gson.annotations.SerializedName

data class RxInfoEntity (
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
)
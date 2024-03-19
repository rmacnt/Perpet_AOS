package pet.perpet.data.api.entity.subscribe

import com.google.gson.annotations.SerializedName

data class SubscibeMedicalProductEntity(
    @SerializedName("dosage")
    val dosage: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("insert_date")
    val insert_date: String?,
    @SerializedName("medical_id")
    val medical_id: Int?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("product_id")
    val product_id: Int?,
    @SerializedName("supplement_id")
    val supplement_id: String?,
    @SerializedName("supplement_type")
    val supplement_type: String?,
    @SerializedName("update_date")
    val update_date: String?
)
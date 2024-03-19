package pet.perpet.domain.model.medical

import com.google.gson.annotations.SerializedName

data class Typeform(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("form_id")
    val form_id: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("insert_date")
    val insert_date: String?,
    @SerializedName("update_date")
    val update_date: String?,
    @SerializedName("insert_user")
    val insert_user: Int?,
    @SerializedName("update_user")
    val update_user: Int?
)

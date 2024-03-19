package pet.perpet.data.api.entity.profile

import com.google.gson.annotations.SerializedName

data class AllergyEntity (
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("is_candidate")
    val is_candidate: Boolean?,
    @SerializedName("is_expired")
    val is_expired: Boolean?,
    @SerializedName("is_main")
    val is_main: Boolean?,
    @SerializedName("insert_user")
    val insert_user: Int?,
    @SerializedName("update_user")
    val update_user: Int?,
    @SerializedName("insert_date")
    val insert_date: String?,
    @SerializedName("update_date")
    val update_date: String?
)
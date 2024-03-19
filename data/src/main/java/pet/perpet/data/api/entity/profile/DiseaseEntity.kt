package pet.perpet.data.api.entity.profile

import com.google.gson.annotations.SerializedName

data class DiseaseEntity (
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("type")
    val type: Any?,
    @SerializedName("main_ctgr_id")
    val main_ctgr_id: Int?,
    @SerializedName("insert_user")
    val insert_user: Int?,
    @SerializedName("update_user")
    val update_user: Int?,
    @SerializedName("insert_date")
    val insert_date: String?,
    @SerializedName("update_date")
    val update_date: String?,
    @SerializedName("_main")
    val _main: Boolean?,
    @SerializedName("_candidate")
    val _candidate: Boolean?,
    @SerializedName("_expired")
    val _expired: Boolean?
)
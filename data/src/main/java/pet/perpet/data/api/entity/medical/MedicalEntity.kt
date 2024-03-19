package pet.perpet.data.api.entity.medical

import com.google.gson.annotations.SerializedName

data class MedicalEntity(
    @SerializedName("changeCheck")
    val changeCheck: Boolean?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("insert_date")
    val insert_date: String?,
    @SerializedName("insert_user")
    val insert_user: Int?,
    @SerializedName("packageComment")
    val packageComment: Any?,
    @SerializedName("packages")
    val packages: ArrayList<PackageEntity>?,
    @SerializedName("resultComment")
    val resultComment: Any?,
    @SerializedName("results")
    val results: ArrayList<ResultEntity>?,
    @SerializedName("round")
    val round: Int?,
    @SerializedName("tags")
    val tags: ArrayList<TagEntity>?,
    @SerializedName("threshold")
    val threshold: Any?,
    @SerializedName("typeformId")
    val typeformId: String?,
    @SerializedName("update_date")
    val update_date: String?,
    @SerializedName("update_user")
    val update_user: Any?

)
package pet.perpet.data.api.entity.bookmark

import com.google.gson.annotations.SerializedName

data class BookmarkSetEntity(

    @SerializedName("id")
    val id: Int?,
    @SerializedName("user_id")
    val user_id: Int?,
    @SerializedName("target_id")
    val target_id: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("insert_date")
    val insert_date: String?,
    @SerializedName("update_date")
    val update_date: String?
)
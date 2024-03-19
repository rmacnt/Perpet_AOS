package pet.perpet.data.api.entity.main

import com.google.gson.annotations.SerializedName

data class FeedEntity(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("user_id")
    val user_id: Int?,
    @SerializedName("target_id")
    val target_id: String?,
    @SerializedName("contents")
    val contents: String?,
    @SerializedName("insert_date")
    val insert_date: String?,
    @SerializedName("update_date")
    val update_date: String?,
    @SerializedName("insert_user")
    val insert_user: Int?,
    @SerializedName("update_user")
    val update_user: Int?
)
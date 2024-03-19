package pet.perpet.domain.model.notification

import com.google.gson.annotations.SerializedName

data class Content (
    @SerializedName("answer_id")
    val answer_id: Any?,
    @SerializedName("contents")
    val contents: String?,
    @SerializedName("delete_yn")
    val delete_yn: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("insert_date")
    val insert_date: String?,
    @SerializedName("insert_user")
    val insert_user: Int?,
    @SerializedName("insert_user_info")
    val insert_user_info: InsertUserInfo?,
    @SerializedName("pet")
    val pet: Pet?,
    @SerializedName("pet_id")
    val pet_id: Int?,
    @SerializedName("tag")
    val tag: String?,
    @SerializedName("tags")
    val tags: List<Tag>?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("update_date")
    val update_date: String?,
    @SerializedName("update_user")
    val update_user: Int?,
    @SerializedName("use_yn")
    val use_yn: String?,
    @SerializedName("views")
    val views: Any?
)
package pet.perpet.data.api.entity.bookmark

import com.google.gson.annotations.SerializedName

data class BookmarkEntity(
    @SerializedName("bookmark")
    val bookmark: Boolean?,
    @SerializedName("bookmark_id")
    val bookmark_id: Int?,
    @SerializedName("contents")
    val contents: List<BookmarkContentEntity>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("image_id")
    val image_id: Int?,
    @SerializedName("insert_date")
    val insert_date: String?,
    @SerializedName("insert_user")
    val insert_user: Int?,
    @SerializedName("main_title")
    val main_title: String?,
    @SerializedName("pet_yn")
    val pet_yn: String?,
    @SerializedName("sort")
    val sort: Int?,
    @SerializedName("summary")
    val summary: String?,
    @SerializedName("tag")
    val tag: String?,
    @SerializedName("tags")
    val tags: List<BookmarkTagEntity>?,
    @SerializedName("top")
    val top: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("type_id")
    val type_id: Int?,
    @SerializedName("update_date")
    val update_date: String?,
    @SerializedName("update_user")
    val update_user: Any?

)
package pet.perpet.domain.model.notification

import com.google.gson.annotations.SerializedName

data class Notice (
    @SerializedName("content")
    val content: ArrayList<Content>?,
    @SerializedName("empty")
    val empty: Boolean?,
    @SerializedName("first")
    val first: Boolean?,
    @SerializedName("last")
    val last: Boolean?,
    @SerializedName("number")
    val number: Int?,
    @SerializedName("numberOfElements")
    val numberOfElements: Int?,
    @SerializedName("pageable")
    val pageable: Pageable?,
    @SerializedName("size")
    val size: Int?,
    @SerializedName("sort")
    val sort: Sort??,
    @SerializedName("totalElements")
    val totalElements: Int?,
    @SerializedName("totalPages")
    val totalPages: Int?

)
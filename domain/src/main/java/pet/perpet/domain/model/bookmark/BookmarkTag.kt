package pet.perpet.domain.model.bookmark

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class BookmarkTag(
    @SerializedName("health_yn")
    val health_yn: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("medical_yn")
    val medical_yn: String?,
    @SerializedName("name")
    val name: String?
): Serializable
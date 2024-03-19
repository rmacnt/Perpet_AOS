package pet.perpet.data.api.request.bookmark

import com.google.gson.annotations.SerializedName

data class BookmarkSetRequest (
    @SerializedName("pet_id")
    val pet_id: Int?,
    @SerializedName("target_id")
    val target_id: Int?,
)
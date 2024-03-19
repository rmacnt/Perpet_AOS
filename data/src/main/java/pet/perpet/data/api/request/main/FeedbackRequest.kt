package pet.perpet.data.api.request.main

import com.google.gson.annotations.SerializedName

data class FeedbackRequest (
    @SerializedName("target_id")
    val target_id: Long?,
    @SerializedName("contents")
    val contents: String?,
)
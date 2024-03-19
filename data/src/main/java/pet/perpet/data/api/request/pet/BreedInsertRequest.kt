package pet.perpet.data.api.request.pet

import com.google.gson.annotations.SerializedName

data class BreedInsertRequest (
    @SerializedName("name")
    val name: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("user_id")
    val user_id: Int?,
)
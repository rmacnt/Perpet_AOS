package pet.perpet.data.api.request.pet

import com.google.gson.annotations.SerializedName

data class AllergyInsertRequest(
    @SerializedName("name")
    val name: String?,
    @SerializedName("user_id")
    val user_id: Int?,
)
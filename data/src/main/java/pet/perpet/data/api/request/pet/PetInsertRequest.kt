package pet.perpet.data.api.request.pet

import com.google.gson.annotations.SerializedName

data class PetInsertRequest (
    @SerializedName("type")
    val type: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("breeds_id")
    val breeds_id: Int?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("age")
    val age: String?,
    @SerializedName("profile")
    val profile: PetProfileRequest?
)
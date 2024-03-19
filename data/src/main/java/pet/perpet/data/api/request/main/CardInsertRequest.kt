package pet.perpet.data.api.request.main

import com.google.gson.annotations.SerializedName

data class CardInsertRequest(
    @SerializedName("type")
    val type: String?,
    @SerializedName("card_number")
    val card_number: String?,
    @SerializedName("expiry")
    val expiry: String?,
    @SerializedName("pwd2digit")
    val pwd2digit: String?,
    @SerializedName("birth")
    val birth: String?
)

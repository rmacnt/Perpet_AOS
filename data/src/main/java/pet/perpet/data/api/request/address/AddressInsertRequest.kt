package pet.perpet.data.api.request.address

import com.google.gson.annotations.SerializedName

data class AddressInsertRequest (
    @SerializedName("recipient")
    val recipient: String?,
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("zipcode")
    val zipcode: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("address_detail")
    val address_detail: String?,
    @SerializedName("memo")
    val memo: String?
)

package pet.perpet.domain.model.address

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("common")
    val common: Common,
    @SerializedName("juso")
    val juso: ArrayList<Juso>
)
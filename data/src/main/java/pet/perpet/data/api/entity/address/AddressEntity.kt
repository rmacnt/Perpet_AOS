package pet.perpet.data.api.entity.address

import com.google.gson.annotations.SerializedName

data class AddressEntity(
    @SerializedName("common")
    val common: CommonEntity,
    @SerializedName("juso")
    val juso: ArrayList<JusoEntity>
)
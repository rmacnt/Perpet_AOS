package pet.perpet.data.api.entity.medical

import com.google.gson.annotations.SerializedName

data class PackageEntity(
    @SerializedName("product")
    val product: ProductEntity?,
    @SerializedName("rxInfo")
    val rxInfo: RxInfoEntity?
)
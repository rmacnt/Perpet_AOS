package pet.perpet.data.api.entity.response

import com.google.gson.annotations.SerializedName

data class BaseAddressPageResponse<T>(
    @SerializedName("results")
    val data: T?
) : BaseResponse<T>()

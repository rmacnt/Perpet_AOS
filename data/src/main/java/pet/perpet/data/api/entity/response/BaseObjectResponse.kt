package pet.perpet.data.api.entity.response

import com.google.gson.annotations.SerializedName

data class BaseObjectResponse<T>(
    @SerializedName("data")
    val data: T?
) : BaseResponse<T>()

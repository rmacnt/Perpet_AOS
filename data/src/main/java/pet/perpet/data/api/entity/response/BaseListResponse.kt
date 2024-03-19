package pet.perpet.data.api.entity.response

import com.google.gson.annotations.SerializedName

data class BaseListResponse<T>(
    @SerializedName("data")
    val data: ArrayList<T>?
) : BaseResponse<T>()

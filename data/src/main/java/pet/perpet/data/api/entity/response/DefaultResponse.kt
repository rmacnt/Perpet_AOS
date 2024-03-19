package pet.perpet.data.api.entity.response

import com.google.gson.annotations.SerializedName

data class DefaultResponse(
    @SerializedName("data")
    val data: Any?
) : BaseResponse<Any>()

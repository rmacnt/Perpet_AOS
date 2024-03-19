package pet.perpet.data.api.entity.response

import com.google.gson.annotations.SerializedName

abstract class BaseResponse<T> {
    @SerializedName("success")
    val success: Boolean = false

    @SerializedName("code")
    val code: Int = -1

    @SerializedName("msg")
    val msg: String = ""

}
package pet.perpet.data.api.entity.response

import com.google.gson.annotations.SerializedName
import pet.perpet.data.api.entity.PageContentsEntity

data class BasePageResponse<T>(

    @SerializedName("data")
    val data: PageContentsEntity<T>?
) : BaseResponse<T>()

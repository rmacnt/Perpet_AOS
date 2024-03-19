package pet.perpet.data.api.request.account

import com.google.gson.annotations.SerializedName

data class AccountMarketingRequest (
    @SerializedName("marketing_agree")
    val marketing_agree: String?
)
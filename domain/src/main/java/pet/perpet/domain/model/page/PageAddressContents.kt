package pet.perpet.domain.model.page

import com.google.gson.annotations.SerializedName

class PageAddressContents<T>(
    @SerializedName("juso")
    val juso: ArrayList<T>?
)

package pet.perpet.data.api

import pet.perpet.data.RetrofitFactory
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.address.AddressEntity
import pet.perpet.data.api.entity.response.BaseAddressPageResponse
import retrofit2.http.GET
import retrofit2.http.Query

object AddressClient {

    val address: Address by lazy {
        RetrofitFactory.create(
            url = "https://business.juso.go.kr/"
        )
            .newBuilder()
            .build()
            .create(Address::class.java)
    }

}

interface Address {

    @GET("addrlink/addrLinkApi.do?currentPage=0&countPerPage=100&confmKey=U01TX0FVVEgyMDIzMTAwNjEwMDQyODExNDE0ODA=&resultType=json")
    fun getAddress(
        @Query("keyword") address: String
    ): ApiCall<BaseAddressPageResponse<AddressEntity>>
}
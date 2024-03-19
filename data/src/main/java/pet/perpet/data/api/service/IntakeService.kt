package pet.perpet.data.api.service

import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.intake.IntakeDataEntity
import pet.perpet.data.api.entity.response.BaseListResponse
import pet.perpet.data.api.entity.response.DefaultResponse
import pet.perpet.data.api.request.intake.CareUpdateRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface IntakeService {

    @GET("care-service/v1/care")
    fun getCareList(
        @Query("start_date") startDate: String? = "",
        @Query("end_date") endDate: String? = "",
    ): ApiCall<BaseListResponse<IntakeDataEntity>>

    @PUT("care-service/v1/care")
    fun setCareUpdate(
        @Body careUpdateRequest: CareUpdateRequest?,
    ): ApiCall<DefaultResponse>

}
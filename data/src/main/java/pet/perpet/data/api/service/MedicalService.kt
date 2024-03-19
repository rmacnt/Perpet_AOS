package pet.perpet.data.api.service

import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.medical.MedicalEntity
import pet.perpet.data.api.entity.medical.TypeformEntity
import pet.perpet.data.api.entity.response.BaseObjectResponse
import pet.perpet.data.api.entity.response.BasePageResponse
import pet.perpet.data.api.entity.response.BasePushResponse
import pet.perpet.data.api.request.account.MedicalHookRequest
import pet.perpet.data.api.request.main.CardInsertRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MedicalService {

    @GET("typeform-service/v1/typeform")
    fun getTypeformInfo(
        @Query("user_id") user_id: Int,
        @Query("pet_id") pet_id: Int,
        @Query("type") type: String,
    ): ApiCall<BaseObjectResponse<TypeformEntity>>

    @GET("medical-service/v1/medical/{id}")
    fun getMedicalInfo(
        @Path("id") id: String,
    ): ApiCall<BaseObjectResponse<MedicalEntity>>

    @GET("medical-service/v1/medical")
    fun getMedicalList(
        @Query("pet_id") petId: Int,
    ): ApiCall<BasePageResponse<MedicalEntity>>



    @POST("typeform-service/v1/typeform/qualtrics/webhook")
    fun getMedicalHook(
        @Body medicalHookRequest: MedicalHookRequest?,
    ): ApiCall<BaseObjectResponse<MedicalEntity>>


}
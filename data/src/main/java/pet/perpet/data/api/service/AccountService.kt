package pet.perpet.data.api.service

import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.account.TokenEntity
import pet.perpet.data.api.entity.account.UserEntity
import pet.perpet.data.api.entity.account.UserLoginEntity
import pet.perpet.data.api.entity.pet.PetEntity
import pet.perpet.data.api.entity.profile.AllergyEntity
import pet.perpet.data.api.entity.profile.BreedEntity
import pet.perpet.data.api.entity.profile.DiseaseEntity
import pet.perpet.data.api.entity.response.BaseObjectResponse
import pet.perpet.data.api.entity.response.BasePageResponse
import pet.perpet.data.api.entity.response.DefaultResponse
import pet.perpet.data.api.request.account.AccountMarketingRequest
import pet.perpet.data.api.request.account.AccountRequest
import pet.perpet.data.api.request.pet.AllergyInsertRequest
import pet.perpet.data.api.request.pet.BreedInsertRequest
import pet.perpet.data.api.request.pet.PetInsertRequest
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AccountService {

    @GET("users")
    fun accountInfo(
        @Path("id") id : Int,
    ): ApiCall<BaseObjectResponse<UserEntity>>

    @FormUrlEncoded
    @POST("user-service/v1/auth/refresh")
    fun token(
        @Field("refreshToken")
        refreshToken: String?,
        @Field("accessToken")
        accessToken: String?
    ): ApiCall<TokenEntity>

    @POST("user-service/v1/auth/social")
    fun accountSocialLogin(
        @Body accountRequest: AccountRequest
    ): ApiCall<BaseObjectResponse<UserLoginEntity>>

    @GET("sign-service/v1/breeds")
    fun getBreedsList(
        @Query("search") search: String?,
        @Query("type") type: String?,
        @Query("limit") limit: Int = 500,
    ): ApiCall<BasePageResponse<BreedEntity>>

    @GET("sign-service/v1/disease")
    fun getDiseaseList(
        @Query("is_main") is_main: Boolean?,
        @Query("limit") limit: Int = 500,

    ): ApiCall<BasePageResponse<DiseaseEntity>>

    @GET("sign-service/v1/disease")
    fun getDiseaseCommentList(
        @Query("is_main") is_main: Boolean?,
        @Query("limit") limit: Int = 500,
        @Query("name") name: String = "",
        @Query("main_ctgr_id") mainCtgrId : Int = -1,

        ): ApiCall<BasePageResponse<DiseaseEntity>>

    @POST("sign-service/v1/breeds/candidate")
    fun setBreed(
        @Body breedInsertRequest: BreedInsertRequest
    ): ApiCall<BaseObjectResponse<BreedEntity>>

    @GET("sign-service/v1/allergy")
    fun getAllergyList(
        @Query("name") name: String? = "",
        @Query("limit") limit: Int = 500,
    ): ApiCall<BasePageResponse<AllergyEntity>>

    @POST("sign-service/v1/allergy/candidate")
    fun setAllergy(
        @Body allergyInsertRequest: AllergyInsertRequest
    ): ApiCall<BaseObjectResponse<AllergyEntity>>

    @POST("user-service/v1/pet")
    fun petInsert(
        @Body petInsertRequest: PetInsertRequest?
    ): ApiCall<BaseObjectResponse<PetEntity>>

    @GET("user-service/v1/pet/{id}")
    fun petInfo(
        @Path("id") id: String
    ): ApiCall<BaseObjectResponse<PetEntity>>

    @GET("user-service/v1/pet/list")
    fun getPetList(): ApiCall<BasePageResponse<PetEntity>>

    @FormUrlEncoded
    @POST("sign-service/v1/authentication/sendsms")
    fun authenticationSendSms (
        @Field("phone") phone: String
    ): ApiCall<DefaultResponse>

    @FormUrlEncoded
    @POST("sign-service/v1/authentication")
    fun authentication (
        @Field("phone") phone: String,
        @Field("auth_num") auth_num: String,
    ): ApiCall<BaseObjectResponse<Any>>


    @GET("user-service/v1/users/dup/nickname/{nickname}")
    fun nicknameCheck (
        @Path("nickname") name: String? = "",
    ): ApiCall<BaseObjectResponse<Any>>

    @GET("user-service/v1/auth/check/signup")
    fun userCheck (
        @Query("id") id: String? = "",
        @Query("type") type: String? = "",
    ): ApiCall<DefaultResponse>

    @GET("user-service/v1/users/{id}")
    fun getUser (
        @Path("id") id: Int
    ): ApiCall<BaseObjectResponse<UserEntity>>

    @FormUrlEncoded
    @PATCH("user-service/v1/users")
    fun setNickNameChange (
        @Field("nickname") nickname: String,
    ): ApiCall<BaseObjectResponse<UserEntity>>

    @POST("user-service/v1/users/withdrawal")
    fun setUserWithdrawal (
    ): ApiCall<BaseObjectResponse<Any>>

    @PATCH("user-service/v1/users/noti/{type}/{value}")
    fun setAlarmSetting (
        @Path("type") type: String,
        @Path("value") value: String
    ): ApiCall<DefaultResponse>

    @PATCH("user-service/v1/users/terms")
    fun setMarketingSetting (
        @Body accountMarketingRequest: AccountMarketingRequest
    ): ApiCall<DefaultResponse>

    @PATCH("user-service/v1/users/fcm-token/{fcmtoken}")
    fun setUserPushToken (
        @Path("fcmtoken") fcmToken: String
    ): ApiCall<DefaultResponse>


}
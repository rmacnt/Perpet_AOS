package pet.perpet.data.api.service

import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.notification.NoticeEntity
import pet.perpet.data.api.entity.notification.PushListEntity
import pet.perpet.data.api.entity.response.BasePageResponse
import pet.perpet.data.api.entity.response.BasePushResponse
import pet.perpet.data.api.entity.response.DefaultResponse
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface NotificationService {

    @GET("bbs-service/v1/bbs")
    fun getBBSList(
        @Query("page") page: Int,
        @Query("fieldType") fieldType: Int,
    ): ApiCall<BasePageResponse<NoticeEntity>>

    @GET("push-service/v1/push")
    fun getPushList(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): ApiCall<BasePageResponse<PushListEntity>>

    @GET("push-service/v1/push")
    fun getPushListCount(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): ApiCall<BasePushResponse>

    @PATCH("push-service/v1/push/{id}/{checked_yn}")
    fun setPushRead(
        @Path("id", encoded = true) id: Int,
        @Path("checked_yn", encoded = true ) checkedYn: String =  "Y",
    ): ApiCall<DefaultResponse>



}
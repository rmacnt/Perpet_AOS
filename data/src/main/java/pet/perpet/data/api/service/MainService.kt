package pet.perpet.data.api.service

import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.address.ListAddressEntity
import pet.perpet.data.api.entity.bookmark.BookmarkEntity
import pet.perpet.data.api.entity.bookmark.BookmarkSetEntity
import pet.perpet.data.api.entity.card.CardChangeEntity
import pet.perpet.data.api.entity.card.CardInsertEntity
import pet.perpet.data.api.entity.card.CardListEntity
import pet.perpet.data.api.entity.main.FeedEntity
import pet.perpet.data.api.entity.main.MainCardEntity
import pet.perpet.data.api.entity.order.InventoryEntity
import pet.perpet.data.api.entity.order.OrderContentEntity
import pet.perpet.data.api.entity.product.ProductEntity
import pet.perpet.data.api.entity.response.BaseListResponse
import pet.perpet.data.api.entity.response.BaseObjectResponse
import pet.perpet.data.api.entity.response.BasePageResponse
import pet.perpet.data.api.entity.response.DefaultResponse
import pet.perpet.data.api.entity.search.SearchArticleEntity
import pet.perpet.data.api.entity.search.SearchProductEntity
import pet.perpet.data.api.entity.search.SearchSimpleEntity
import pet.perpet.data.api.entity.search.SearchTagEntity
import pet.perpet.data.api.entity.tracker.TrackerEntity
import pet.perpet.data.api.request.address.AddressEditRequest
import pet.perpet.data.api.request.address.AddressInsertRequest
import pet.perpet.data.api.request.bookmark.BookmarkSetRequest
import pet.perpet.data.api.request.main.CardInsertRequest
import pet.perpet.data.api.request.main.FeedbackRequest
import pet.perpet.data.api.request.main.SubscribeRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface MainService {


    @GET("mcard-service/v1/mcard")
    fun getMainCard(
        @Query("user_id") userId: String? = "",
        @Query("pet_id") petId: String? = "",
    ): ApiCall<BasePageResponse<MainCardEntity>>

    @GET("sign-service/v1/search/type")
    fun getSearchArticle(
        @Query("page") page: Int = 0,
        @Query("type") type: String? = "article",
        @Query("search") search: String? = "",
    ): ApiCall<BasePageResponse<SearchArticleEntity>>

    @GET("sign-service/v1/search/type")
    fun getSearchProduct(
        @Query("page") page: Int = 0,
        @Query("type") type: String? = "product",
        @Query("search") search: String? = ""
    ): ApiCall<BasePageResponse<SearchProductEntity>>

    @GET("sign-service/v1/search/type")
    fun getSearchTag(
        @Query("page") page: Int = 0,
        @Query("type") type: String? = "tag",
        @Query("search") search: String? = ""
    ): ApiCall<BasePageResponse<SearchTagEntity>>

    @GET("sign-service/v1/search")
    fun getIntegratedSearch(
        @Query("search") search: String?,
    ): ApiCall<BaseListResponse<SearchSimpleEntity>>


    @GET("card-service/v1/cards")
    fun getCardList(): ApiCall<BasePageResponse<CardListEntity>>

    @POST("card-service/v1/cards")
    fun setCardInsert(
        @Body cardInsertRequest: CardInsertRequest?,
    ): ApiCall<DefaultResponse>

    @GET("user-service/v1/address")
    fun getAddressList(): ApiCall<BasePageResponse<ListAddressEntity>>

    @PUT("user-service/v1/address")
    fun editAddress(
        @Body addressEditRequest: AddressEditRequest?,
    ): ApiCall<BaseObjectResponse<ListAddressEntity>>


    @DELETE("card-service/v1/cards/{id}")
    fun editCard(
        @Body addressEditRequest: AddressEditRequest?,
    ): ApiCall<BaseObjectResponse<ListAddressEntity>>


    @POST("user-service/v1/address")
    fun setAddressInsert(
        @Body addressInsertRequest: AddressInsertRequest?,
    ): ApiCall<BaseObjectResponse<ListAddressEntity>>

    @POST("order-service/v1/orders/subscribe")
    fun setSubscribe(
        @Body subscribeRequest: SubscribeRequest?,
    ): ApiCall<DefaultResponse>

    @DELETE("user-service/v1/address/{id}")
    fun deleteAddress(
        @Path("id", encoded = true) id: Int?,
    ): ApiCall<DefaultResponse>


    @DELETE("card-service/v1/cards/{id}")
    fun deleteCard(
        @Path("id", encoded = true) id: Int?,
    ): ApiCall<DefaultResponse>


    @GET("product-service/v1/products")
    fun getProductList(
        @Query("category") category: String? = "츄르",
    ): ApiCall<BasePageResponse<ProductEntity>>

    @GET("mcard-service/v1/bookmarks")
    fun getBookmark(
        @Query("pet_id") petId: String? = "",
    ): ApiCall<BasePageResponse<BookmarkEntity>>

    @GET("inventory-service/v1/inventory")
    fun getOrderHistory(): ApiCall<BasePageResponse<InventoryEntity>>

    @FormUrlEncoded
    @PATCH("inventory-service/v1/inventory/address")
    fun setOrderAddressChange (
        @Field("id") id: String, //inventory_id(=지난구독내역의 고유 번호). 필수값
        @Field("address_id") addressId: String
    ): ApiCall<BaseObjectResponse<InventoryEntity>>

    @PUT("user-service/v1/pet/main/{id}")
    fun setPetSelect(
        @Path("id", encoded = true) id: Int?,
    ): ApiCall<DefaultResponse>

    @POST("mcard-service/v1/bookmarks")
    fun setBookmark(
        @Body bookmarkSetRequest: BookmarkSetRequest?,
    ): ApiCall<BaseObjectResponse<BookmarkSetEntity>>

    @DELETE("mcard-service/v1/bookmarks/{id}")
    fun deleteBookmark(
        @Path("id", encoded = true) id: Int?,
    ): ApiCall<DefaultResponse>

    @POST("mcard-service/v1/mcard/comments")
    fun setFeed(
        @Body feedbackRequest: FeedbackRequest?,
    ): ApiCall<BaseObjectResponse<FeedEntity>>

    @GET("inventory-service/v1/inventory/{id}")
    fun getOrderDetail(
        @Path("id", encoded = true) id: Int?,
    ): ApiCall<BaseObjectResponse<InventoryEntity>>

    //    @POST("order-service/v1/orders/unsubscribe/{id}")
    @POST("payment-service/v1/payment/cancel/{inventory_id}")
    fun setOrderCancel(
        @Path("inventory_id", encoded = true) id: Int?,
    ): ApiCall<DefaultResponse>

    @POST("payment-service/v1/payment/cancel/{inventory_id}")
    fun setPaymentCancel(
        @Path("inventory_id", encoded = true) id: Int?,
    ): ApiCall<DefaultResponse>

    @GET("order-service/v1/orders/{id}")
    fun getNowOrderDetail(
        @Path("id", encoded = true) id: Int?,
    ): ApiCall<BaseObjectResponse<OrderContentEntity>>

    @POST("order-service/v1/orders/unsubscribe/{id}")
    fun setNowOrderCancel(
        @Path("id", encoded = true) id: Int?,
    ): ApiCall<DefaultResponse>

    @FormUrlEncoded
    @PATCH("order-service/v1/orders/cards")
    fun setCardChange (
        @Field("id") id: String,
        @Field("card_id") cardId: String
    ): ApiCall<BaseObjectResponse<CardChangeEntity>>

    @FormUrlEncoded
    @PATCH("order-service/v1/orders/address")
    fun setAddressChange (
        @Field("id") id: String,
        @Field("address_id") addressId: String
    ): ApiCall<BaseObjectResponse<OrderContentEntity>>

    @PATCH("order-service/v1/orders/moveback/{type}/{id}")
    fun setOrderMoveBack (
        @Path("type" , encoded = true) type: String,
        @Path("id" ,  encoded = true) id: Int
    ): ApiCall<DefaultResponse>

    @GET("mcard-service/v1/mcard/{id}")
    fun getSearchCard(
        @Path("id" ,  encoded = true) id: Int,
        @Query("user_id") userId: String? = ""
    ): ApiCall<BaseObjectResponse<SearchArticleEntity>>

    @GET("product-service/v1/products/{id}")
    fun getSearchProduct(
        @Path("id",  encoded = true) id: Int
    ): ApiCall<BaseObjectResponse<SearchProductEntity>>

    @GET("tracker-service/v1/tracker/{carrier}/{trackerid}")
    fun getTracker(
        @Path("carrier" ,  encoded = true) carrier: String,
        @Path("trackerid" ,  encoded = true) trackerid: String
    ): ApiCall<BaseObjectResponse<TrackerEntity>>

}
package pet.perpet.data.api.request.main

import com.google.gson.annotations.SerializedName


data class SubscribeRequest (
    @SerializedName("pet_id")
    val pet_id: String?, //반려동물 고유 번호
    @SerializedName("medical_id")
    val medical_id: String?, //문진 고유 번호
    @SerializedName("card_id")
    val card_id: String?, //카드 고유 번호
    @SerializedName("address_id")
    val address_id: String?, //배송지 고유 번호
    @SerializedName("pay_method")
    val pay_method: String?, //결제수단 (card/kakaopay/naverpay)
    @SerializedName("amount")
    val amount: String?, //(영양제 패키지 금액 + 추가 츄르 상품 금액)
    @SerializedName("addProducts")
    val addProducts: ArrayList<AddProductsRequest>?
)

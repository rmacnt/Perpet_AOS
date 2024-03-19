package pet.perpet.data.api.request.main

import com.google.gson.annotations.SerializedName

data class AddProductsRequest (
    @SerializedName("product_id")
    val product_id: Int?, //추가 츄르 상품 고유 번호
    @SerializedName("quantity")
    val quantity: Int?, //추가 츄르 상품 개수
    @SerializedName("price")
    val price: Double?, //추가 츄르 상품 가격
    @SerializedName("totalPrice")
    val totalPrice: Double? //츄르 상품 총 가격 (quantity * price)
)
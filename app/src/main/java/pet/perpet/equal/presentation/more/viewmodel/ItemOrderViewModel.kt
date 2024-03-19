package pet.perpet.equal.presentation.more.viewmodel

import android.graphics.drawable.Drawable
import android.view.View
import pet.perpet.domain.model.order.Inventory
import pet.perpet.equal.R
import pet.perpet.equal.presentation.getDrawable
import pet.perpet.equal.presentation.goOrderDetail

class ItemOrderViewModel(var model: Inventory? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val type: Boolean?
        get() = model?.status == "orderCanceled"

    val title: String?
        get() = model?.pet_name + " 정기구독"

    val date: String?
        get() = "${model?.toDiff()}"

    val orderBg: Drawable?
        get() = getDrawable(
            when (model?.status) {
                "paymentPending", "shippingInProgress", "orderConfirmed", "exchangeRequested", "reshipmentInProgress" -> R.drawable.order_bg_1
                "paymentCompleted", "preparingNutritionalSupplements" -> R.drawable.order_bg_2
                "delivered", "exchangePickupCompleted", "exchangeCompleted", "returnPickupCompleted", "refundCompleted" -> R.drawable.black_radius_4
                "orderCanceled", "exchangeRejected", "returnRequested", "returnApproved", "returnRejected" -> R.drawable.order_bg_3
                else -> R.drawable.order_bg_3
            }
        )

    val typeTitle: String?
        get() = when (model?.status) {
            "paymentPending" -> "결제대기"
            "paymentCompleted" -> "결제완료"
            "preparingNutritionalSupplements" -> "영양제 조합중"
            "shippingInProgress" -> "배송중"
            "delivered" -> "배송완료"
            "orderCanceled" -> "주문취소"
            "orderConfirmed" -> "구매 확정"
            "exchangeRequested" -> "교환신청"
            "exchangePickupCompleted" -> "교환 수거 완료"
            "exchangeRejected" -> "교환 거부"
            "reshipmentInProgress" -> "재배송중"
            "exchangeCompleted" -> "교환완료"
            "returnRequested" -> "반품신청"
            "returnPickupCompleted" -> "반품수거완료"
            "returnApproved" -> "반품 승인"
            "returnRejected" -> "반품 거부"
            "refundCompleted" -> "환불완료"
            "exchangeRejected" -> "교환 거부"
            else -> "알 수 없음"
        }

    //======================================================================
    // Public Methods
    //======================================================================

    fun onDetailClick(view: View) {

        view.context?.let {
            model?.id?.let { item ->
                it.goOrderDetail(item.toString())
            }

        }
    }
}
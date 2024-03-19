package pet.perpet.equal.presentation.more.viewmodel.shipping

import android.view.View
import pet.perpet.data.nonnull
import pet.perpet.domain.model.address.Juso
import pet.perpet.equal.presentation.sign.Channel
import pet.perpet.framework.util.Logger

open class ItemAddressViewModel  (var model: Juso? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val zipNo: String?
        get() = model?.zipNo

    val jibunAddress: String?
        get() = model?.jibunAddr

    val roadAddress: String?
        get() = model?.roadAddr

    val serviceResult: Boolean?
        get() = model?.siNm?.contains("서울").nonnull() || model?.siNm?.contains("경기").nonnull() || model?.siNm?.contains("인천").nonnull()


    //======================================================================
    // Public Methods
    //======================================================================

    open fun onClick(view: View) {
        Logger.d("ItemAddressViewModel", "onClick > ${model}")
        model?.let {
            Channel.jusoClick.send(it)
        }
    }
}
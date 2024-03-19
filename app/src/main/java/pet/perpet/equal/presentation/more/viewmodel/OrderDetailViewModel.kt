package pet.perpet.equal.presentation.more.viewmodel

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.text.Spanned
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zoyi.channel.plugin.android.ChannelIO
import com.zoyi.channel.plugin.android.open.config.BootConfig
import com.zoyi.channel.plugin.android.open.enumerate.BootStatus
import com.zoyi.channel.plugin.android.open.model.Profile
import com.zoyi.channel.plugin.android.open.model.User
import kotlinx.coroutines.launch
import pet.perpet.data.nonnull
import pet.perpet.domain.UserStore
import pet.perpet.domain.asDate
import pet.perpet.domain.usecase.address.AddressChangeUseCase
import pet.perpet.domain.usecase.medical.MedicalResultUseCase
import pet.perpet.domain.usecase.order.OrderAddressChangeUseCase
import pet.perpet.domain.usecase.order.OrderCancelUseCase
import pet.perpet.domain.usecase.order.OrderDetailUseCase
import pet.perpet.equal.R
import pet.perpet.equal.presentation.base.ui.AppAlertDialog
import pet.perpet.equal.presentation.getDrawable
import pet.perpet.equal.presentation.goSupplementPackage
import pet.perpet.equal.presentation.goTracker
import pet.perpet.equal.presentation.more.fragment.OrderDetailFragmentArgs
import pet.perpet.equal.presentation.subscribe.fragment.BottomAddressSettingFragment
import pet.perpet.equal.presentation.supportFragmentManager
import pet.perpet.equal.support.navigation.showAddressSetting
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.util.http.HtmlFactory
import java.text.DecimalFormat
import java.text.SimpleDateFormat

class OrderDetailViewModel (fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    private val args by lazy {
        OrderDetailFragmentArgs.fromBundle(arguments)
    }

    val name: String?
        get() = petName.value

    val orderNumber: String?
        get() = petOrderNumber.value

    val price: String?
        get() = petPrice.value

    val date: String?
        get() = petDate.value

    val orderName: String?
        get() = petOrderName.value

    val orderYn: Boolean?
        get() = petOrderStatus.value == "paymentCompleted"

    val orderBg: Drawable?
        get() = getDrawable(
            when (petOrderStatus.value) {
                "paymentPending", "shippingInProgress" , "orderConfirmed" , "exchangeRequested" , "reshipmentInProgress"->  R.drawable.order_bg_1
                "paymentCompleted", "preparingNutritionalSupplements" -> R.drawable.order_bg_2
                "delivered", "exchangePickupCompleted" , "exchangeCompleted" , "returnPickupCompleted" ,"refundCompleted"-> R.drawable.black_radius_4
                "orderCanceled" , "exchangeRejected" , "returnRequested" , "returnApproved" , "returnRejected"-> R.drawable.order_bg_3
                else -> R.drawable.order_bg_3
            }
        )

    val addressVisible: Boolean?
        get() =  when (petOrderStatus.value) {
            "paymentPending" , "paymentCompleted" -> true
            else-> false
        }

    val orderType: String?
        get() = when (petOrderStatus.value) {
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

    val detail: Spanned?
        get() = getString(R.string.detail_1_u)
            .orEmpty().run { HtmlFactory.fromHtml(this) }

    val service: Spanned?
        get() = getString(R.string.order_history_comment2)
            .orEmpty().run { HtmlFactory.fromHtml(this) }



    val petOrderStatus: MutableLiveData<String> = MutableLiveData("")
    val petOrderYn: MutableLiveData<String> = MutableLiveData("")
    val petOrderId: MutableLiveData<Int> = MutableLiveData(0)
    val petName: MutableLiveData<String> = MutableLiveData("")
    val petOrderNumber: MutableLiveData<String> = MutableLiveData("")
    val petOrderName: MutableLiveData<String> = MutableLiveData("")
    val petPrice: MutableLiveData<String> = MutableLiveData("")
    val petDate: MutableLiveData<String> = MutableLiveData("")
    val totalPrice: MutableLiveData<String> = MutableLiveData("")
    val productAddress: MutableLiveData<String> = MutableLiveData("")
    val productAddressName: MutableLiveData<String> = MutableLiveData("")
    val allTotalPrice: MutableLiveData<String> = MutableLiveData("")
    val petMedicalId: MutableLiveData<Int> = MutableLiveData(0)
    val petId: MutableLiveData<Int> = MutableLiveData(0)
    val carrier: MutableLiveData<String> = MutableLiveData("")
    val trackerId: MutableLiveData<String> = MutableLiveData("")
    val trackerPetName: MutableLiveData<String> = MutableLiveData("")
    val trackerRecipientName: MutableLiveData<String> = MutableLiveData("")

    val productCard: MutableLiveData<String> =
        MutableLiveData(getString(R.string.subscribe_comment19))

    val totalPriceText: String?
        get() = totalPrice.value


    //======================================================================
    // Public Methods
    //======================================================================

    fun onFinishClick(view: View) {
        activity?.let {
            it.finish()
        }
    }

    fun onServiceClick(view: View) {
        ChannelIO.showMessenger(activity)
    }

    fun onAddressInsertClick(view: View) {
        context?.let {
            it.supportFragmentManager()?.let {
                activity?.showAddressSetting { address ->

                    OrderAddressChangeUseCase().parameter2 {
                        this.id = args.id.nonnull()
                        this.addressId = address.id.toString()
                    }.success {

                        it?.let {
                            productAddress.value = it?.address?.address
                            productAddressName.value = "${it?.address?.recipient} / ${it?.address?.phone}"

                            fragment?.parentFragmentManager?.let {
                                it.beginTransaction()
                                    .attach(BottomAddressSettingFragment())
                                    .commitAllowingStateLoss()
                            }
                            Toast.makeText(activity , getString(R.string.subscribe_comment70) , Toast.LENGTH_SHORT).show()
                        }
                        executeViewBinding()

                    }.fail {
                    }.execute()


                }
            }
        }

    }

    fun onProductDetailClick(view: View) {
        activity?.let {
            if(petMedicalId.value.nonnull() > 0) {
                MedicalResultUseCase().parameter2 {
                    this.id = petMedicalId.value.nonnull()
                }.success {
                    it?.let {
                        activity?.goSupplementPackage(
                            it,
                            petId.value.toString(),
                            petName.value.nonnull(),
                            true
                        )
                    }
                }.fail {
                }.execute()
            }
        }
    }

    fun onCancelClick(view: View) {
        activity?.let {activity->
            AppAlertDialog(
                activity,
                getString(R.string.dialog_title),
                getString(R.string.subscribe_cancel),
                getString(R.string.subscribe_comment56)

            ).show(
                onClickNegative = {
                    activity.finish()
                },
                onClickPositive = {
                    viewModelScope.launch {
                        OrderCancelUseCase().parameter2 {
                            this.id = args.id.nonnull().toInt()
                        }.success {
                            Toast.makeText(view.context , "결제가 취소되었습니다", Toast.LENGTH_SHORT).show()
                            activity?.finish()
                        }.fail {

                        }.execute()
                    }
                    it.dismiss()
                }

            )
        }

    }

    fun onTrackerClick(view: View) {
        activity?.let {activity->
            if(trackerId.value.nonnull().isNotEmpty() &&  carrier.value.nonnull().isNotEmpty()) {
                activity.goTracker(trackerId.value.nonnull() , carrier.value.nonnull() ,trackerPetName.value.nonnull() , trackerRecipientName.value.nonnull() )
            } else {
                AppAlertDialog(
                    activity,
                    getString(R.string.dialog_title),
                    getString(R.string.order_history_comment10),
                    getString(R.string.app_dialog_action_confirm)

                ).show(
                    onClickPositive = {
                        it.dismiss()
                    }

                )
            }

        }

    }

    fun getLoad() {

        OrderDetailUseCase().parameter2 {
            this.id = args.id?.toInt().nonnull()
        }.success {
            it?.let {
                petName.value = it.pet_name.nonnull() + " 정기구독"
                petOrderNumber.value = it.order_number.nonnull()
                petPrice.value =  String.format(getString(R.string.price) , DecimalFormat("#,###").format(it.amount))
                toDiff(it.payment_dt.nonnull())
                petOrderId.value = it.order_id
                petId.value = it.pet_id
                petMedicalId.value = it.medical_id
                petOrderStatus.value = it.status
                petOrderYn.value = if(it.status == "orderCanceled") "Y" else "N"
                productCard.value = "${it.cards?.card_name} / ${it.cards?.card_number}"
                productAddress.value = it.address?.address
                productAddressName.value = "${it.address?.recipient} / ${it.address?.phone}"
                petOrderName.value =  it.pet_name.nonnull() + " 영양제 패키지 (30일)"

                carrier.value = it.delivery?.carrier.nonnull()
                trackerId.value = it.delivery?.trackerid.nonnull()

                trackerPetName.value = it.pet_name
                trackerRecipientName.value = it.address?.recipient
                allTotalPrice.value =
                    String.format(
                        getString(R.string.price),
                        DecimalFormat("#,###").format((it.amount))
                    )

                executeViewBinding()
            }
        }.fail {
        }.execute()

    }

    @SuppressLint("SimpleDateFormat")
    fun toDiff(date: String) {
        val dst = date.asDate()
        if (dst != null) {
            val formatter = SimpleDateFormat("yyyy. MM. dd HH:mm")
            petDate.value = formatter.format(dst)
        }
    }
}
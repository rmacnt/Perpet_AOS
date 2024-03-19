package pet.perpet.equal.presentation.subscribe.viewmodel

import android.text.Spanned
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pet.perpet.data.api.entity.response.DefaultResponse
import pet.perpet.data.nonnull
import pet.perpet.domain.usecase.address.AddressChangeUseCase
import pet.perpet.domain.usecase.card.CardChangeUseCase
import pet.perpet.domain.usecase.main.MainUseCase
import pet.perpet.domain.usecase.medical.MedicalResultUseCase
import pet.perpet.domain.usecase.order.OrderMoveBackUseCase
import pet.perpet.domain.usecase.order.OrderNowCancelUseCase
import pet.perpet.domain.usecase.order.OrderNowDetailUseCase
import pet.perpet.equal.R
import pet.perpet.equal.presentation.base.ui.AppAlertDialog
import pet.perpet.equal.presentation.goSupplementPackage
import pet.perpet.equal.presentation.subscribe.fragment.BottomAddressSettingFragment
import pet.perpet.equal.presentation.subscribe.fragment.SubscriptionDetailFragmentArgs
import pet.perpet.equal.presentation.supportFragmentManager
import pet.perpet.equal.support.navigation.showAddressSetting
import pet.perpet.equal.support.navigation.showPaymentSetting
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.util.http.HtmlFactory
import java.text.DecimalFormat

class SubscriptionDetailViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    private val args by lazy {
        SubscriptionDetailFragmentArgs.fromBundle(arguments)
    }


    val useCase = MainUseCase()

    val name: String?
        get() = petName.value

    val subscibeCancel: Spanned?
        get() =HtmlFactory.fromHtml(getString(R.string.order_history_comment8))

    val detail: Spanned?
        get() =HtmlFactory.fromHtml(getString(R.string.detail_u))


    val price: String?
        get() = petPrice.value

    val date: String?
        get() = petDate.value

    val orderName: String?
        get() = petOrderName.value

    val totalPriceText: String?
        get() = allTotalPrice.value

    val petOrderName: MutableLiveData<String> = MutableLiveData("")
    val allTotalPrice: MutableLiveData<String> = MutableLiveData("")
    val productAddress: MutableLiveData<String> = MutableLiveData("")
    val productAddressName: MutableLiveData<String> = MutableLiveData("")
    val petName: MutableLiveData<String> = MutableLiveData("")
    val petPrice: MutableLiveData<String> = MutableLiveData("")
    val petDate: MutableLiveData<String> = MutableLiveData("")
    val petNameOrgin: MutableLiveData<String> = MutableLiveData("")
    val petMedicalId: MutableLiveData<Int> = MutableLiveData(0)
    val petId: MutableLiveData<Int> = MutableLiveData(0)
    val productCard: MutableLiveData<String> =
        MutableLiveData(getString(R.string.subscribe_comment19))
    //======================================================================
    // Public Methods
    //======================================================================

    fun onAddressInsertClick(view: View) {
        context?.let {
            it.supportFragmentManager()?.let {
                activity?.showAddressSetting { address ->

                    AddressChangeUseCase().parameter2 {
                        this.id = args.id.nonnull()
                        this.addressId = address.id.toString()
                    }.success {
                        productAddress.value = address.address
                        productAddressName.value = "${address.recipient} / ${address.phone}"

                        fragment?.parentFragmentManager?.let {
                            it.beginTransaction()
                                .attach(BottomAddressSettingFragment())
                                .commitAllowingStateLoss()
                        }
                        Toast.makeText(activity , getString(R.string.subscribe_comment70) , Toast.LENGTH_SHORT).show()

                        executeViewBinding()

                    }.fail {
                    }.execute()


                }
            }
        }

    }


    fun onPaymentInsertClick(view: View) {
        context?.let {
            it.supportFragmentManager()?.let {
                activity?.showPaymentSetting { card ->

                    CardChangeUseCase().parameter2 {
                        this.id = args.id.nonnull()
                        this.cardId = card.id.toString()
                    }.success {
                        productCard.value = "${card.card_name} / ${card.card_number}"

                        fragment?.parentFragmentManager?.let {
                            it.beginTransaction()
                                .attach(BottomAddressSettingFragment())
                                .commitAllowingStateLoss()
                        }
                        Toast.makeText(activity , getString(R.string.subscribe_comment70) , Toast.LENGTH_SHORT).show()

                        executeViewBinding()

                    }.fail {
                    }.execute()

                    executeViewBinding()
                }
            }
        }
    }

    fun onHomeClick(view: View) {
        activity?.let {
            it.finish()
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
                            petNameOrgin.value.nonnull(),
                            true
                        )
                    }
                }.fail {
                }.execute()
            }
        }
    }

    fun onOrderMoveClick(view: View) {
        activity?.let { activity ->


            AppAlertDialog(
                activity,
                getString(R.string.dialog_title),
                getString(R.string.subscribe_comment59),
                getString(R.string.subscribe_comment60),
                getString(R.string.app_dialog_action_cancel)

            ).show(
                onClickNegative = {
                    activity.finish()
                },
                onClickPositive = {
                    viewModelScope.launch {
                        OrderMoveBackUseCase().parameter2 {
                            this.type = view.tag.toString()
                            this.id = args.id.nonnull().toInt()
                        }.success {
                            Toast.makeText(
                                activity,
                                getString(R.string.subscribe_comment61),
                                Toast.LENGTH_SHORT
                            ).show()


                            getLoad()
                        }.fail {
                        }.execute()
                    }
                    it.dismiss()
                }
            )

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
                        OrderNowCancelUseCase().parameter2 {
                            this.id = args.id.nonnull().toInt()
                        }.success {
                            if(it is DefaultResponse) {
                                if(it.success ) {
                                    Toast.makeText(activity, "구독이 취소되었습니다.", Toast.LENGTH_SHORT).show()
                                    activity.finish()
                                } else {
                                    Toast.makeText(activity, it.msg, Toast.LENGTH_SHORT).show()
                                    activity.finish()
                                }
                            }
                        }.fail {
                        }.execute()
                    }
                    it.dismiss()
                }

            )
        }

    }


    fun getLoad() {

        OrderNowDetailUseCase().parameter2 {
            this.id = args.id?.toInt()
        }.success {
            it?.let {
                petName.value = it.pet_name + " 정기구독"
                petPrice.value = String.format(
                    getString(R.string.price),
                    DecimalFormat("#,###").format(it.amount)
                )
                petNameOrgin.value = it.pet_name
                petId.value = it.pet_id
                petMedicalId.value = it.latest_medical_id
                petDate.value = it.next_pay_date
                productCard.value = "${it.cards?.card_name} / ${it.cards?.card_number}"
                productAddress.value = it.address?.address
                productAddressName.value = "${it.address?.recipient} / ${it.address?.phone}"
                petOrderName.value = it.pet_name.nonnull().toString() + "영양제 패키지 (30일)"

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
}
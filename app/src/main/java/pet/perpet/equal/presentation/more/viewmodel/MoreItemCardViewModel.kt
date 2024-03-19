package pet.perpet.equal.presentation.more.viewmodel

import android.graphics.drawable.Drawable
import android.view.View
import androidx.fragment.app.Fragment
import pet.perpet.data.nonnull
import pet.perpet.domain.UserStore
import pet.perpet.domain.usecase.medical.MedicalResultUseCase
import pet.perpet.equal.R
import pet.perpet.equal.presentation.base.ui.AppAlertDialog
import pet.perpet.equal.presentation.getColor
import pet.perpet.equal.presentation.getDrawable
import pet.perpet.equal.presentation.getSubscribeDetail
import pet.perpet.equal.presentation.goBookmark
import pet.perpet.equal.presentation.goExaminationResult
import pet.perpet.equal.presentation.goHeathStart
import pet.perpet.equal.presentation.goSubscribe
import pet.perpet.equal.presentation.goSupplementPackage
import pet.perpet.equal.presentation.more.fragment.MoreItemCardFragmentArgs
import pet.perpet.framework.fragment.UseViewModel

class MoreItemCardViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Variables
    //======================================================================

    private val args by lazy {
        MoreItemCardFragmentArgs.fromBundle(arguments)
    }

    val name: String?
        get() = args.pet?.name

    val sex: String?
        get() = if (args.pet?.gender == "F") {
            "여아"
        } else {
            "남아"
        }

    val type: String?
        get() = "${args.pet?.breed} ${args.pet?.age}"

    val message: String?
        get() = if (args.pet?.latest_medical_id == null) {
            "문진을 진행해주세요."
        } else if (args.pet?.latest_order_id == null) {
            "영양제 패키지를 구독해보세요."
        } else if (args.pet?.next_pay_date != null) {
            "결제 예정일: ${args.pet?.next_pay_date}"
        } else {
            "문진을 진행해주세요."
        }

    val medicalImage: Drawable?
        get() = if (args.pet?.latest_medical_id == null) {
            getDrawable(R.drawable.ic_more_result_white)
        } else {
            getDrawable(R.drawable.ic_more_result_black)
        }

    val medicalColor: Int?
        get() = if (args.pet?.latest_medical_id == null) {
            getColor(R.color.white)
        } else {
            getColor(R.color.black)
        }

    val medicalString: String?
        get() = if (args.pet?.latest_medical_id == null) {
            getString(R.string.more_comment25)
        } else {
            getString(R.string.more_comment13)
        }

    val medicalBg: Drawable?
        get() = if (args.pet?.latest_medical_id == null) {
            getDrawable(R.drawable.more_check_drawable)
        } else {
            getDrawable(R.drawable.white_radius_8)
        }

    val medical: Boolean?
        get() = args.pet?.latest_medical_id == null

    val subscribe: Boolean?
        get() = if (args.pet?.latest_medical_id == null) {
            false
        } else args.pet?.latest_order_id == null

    val subscribeImage: Drawable?
        get() = if (args.pet?.latest_medical_id == null) {
            getDrawable(R.drawable.ic_more_subscribe_888)
        } else if (args.pet?.latest_order_id == null) {
            getDrawable(R.drawable.ic_more_subscribe_white)
        } else {
            getDrawable(R.drawable.ic_more_subscribe)
        }

    val subscribeColor: Int?
        get() = if (args.pet?.latest_medical_id == null) {
            getColor(R.color.text_hint_color)
        } else if (args.pet?.latest_order_id == null) {
            getColor(R.color.white)
        } else {
            getColor(R.color.black)
        }

    val subscribeString: String?
        get() = if (args.pet?.latest_medical_id == null) {
            getString(R.string.more_comment14)
        } else if (args.pet?.latest_order_id == null) {
            getString(R.string.more_comment26)
        } else {
            getString(R.string.more_comment14)
        }

    val subscribeBg: Drawable?
        get() = if (args.pet?.latest_medical_id == null) {
            getDrawable(R.drawable.solid_444_888_stork_radius8)
        } else if (args.pet?.latest_order_id == null) {
            getDrawable(R.drawable.more_check_drawable)
        } else {
            getDrawable(R.drawable.white_radius_8)
        }

    //======================================================================
    // Public Methods
    //======================================================================

    fun onMedicalClick(view: View) {
        context?.let {
            if (args.pet?.latest_medical_id == null) {
                it.goHeathStart()
            } else {
                it.goExaminationResult(
                    args.pet?.name.nonnull(),
                    args.pet?.latest_medical_id.nonnull().toString(),
                    args.pet?.id.toString(),
                    args.pet?.latest_order_id != null
                )
            }
        }

    }

    fun onBookMarkClick(view: View) {
        context?.let {
            it.goBookmark(args.pet?.id.toString().nonnull())
        }

    }


    fun onSubscribeClick(view: View) {
        if (args.pet?.latest_medical_id != null) {
            context?.let {
                MedicalResultUseCase().parameter2 {
                    this.id = args.pet?.latest_medical_id.nonnull()
                }.success {
                    it?.let {
                        if (args.pet?.has_subscription != null && args.pet?.has_subscription.nonnull() && args.pet?.latest_order_id != null) {
                            view.context.getSubscribeDetail(
                                args.pet?.latest_order_id.nonnull().toString(),
                                args.pet?.name.nonnull()
                            )
                        } else {
                            activity?.goSupplementPackage(
                                it, args.pet?.id.toString().nonnull(),
                                args.pet?.name.nonnull(), false
                            )
                        }
                    }
                }.fail {
                }.execute()
            }
        } else {
            activity?.let { activity ->
                AppAlertDialog(
                    activity,
                    getString(R.string.dialog_title),
                    getString(R.string.more_comment27),
                    getString(R.string.app_dialog_action_confirm)

                ).show(
                    onClickPositive = {
                        it.dismiss()
                    }
                )
            }
        }
    }
}
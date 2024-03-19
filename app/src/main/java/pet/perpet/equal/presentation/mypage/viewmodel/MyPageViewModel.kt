package pet.perpet.equal.presentation.mypage.viewmodel

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pet.perpet.data.nonnull
import pet.perpet.domain.UserStore
import pet.perpet.domain.usecase.account.AccountUserInfoUseCase
import pet.perpet.domain.usecase.account.AccountWithdrawalUseCase
import pet.perpet.domain.usecase.order.OrderCancelUseCase
import pet.perpet.equal.R
import pet.perpet.equal.presentation.base.ui.AppAlertDialog
import pet.perpet.equal.presentation.createStartActivityIntent
import pet.perpet.equal.presentation.goHome
import pet.perpet.equal.presentation.goNickNameEdit
import pet.perpet.equal.presentation.logout
import pet.perpet.framework.fragment.UseViewModel

class MyPageViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    var result: Boolean = false

    val nicknameValue: String?
        get() = nickname.value

    val loginType: String?
        get() =  when (UserStore.recent) {
            "KAKAO" -> getString(R.string.mypage_comment2)
            "NAVER" -> getString(R.string.mypage_comment19)
            "GOOGLE" -> getString(R.string.mypage_comment20)
            else -> getString(R.string.mypage_comment2)
        }


    val nickname: MutableLiveData<String> = MutableLiveData("")

    //======================================================================
    // Public Methods
    //======================================================================

    fun onSubmitClick(view: View) {
        context?.let {
            activity?.finish()
        }
    }

    fun onHomeClick(view: View) {
        context?.let {
            it.goHome()
        }

    }

    fun onFinishClick(view: View) {
        activity?.let {
            it.finish()
        }

    }

    fun onNiCkNameClick(view: View) {
        activity?.let {
            it.goNickNameEdit()
        }

    }

    fun onWithdrawalClick(view: View) {
        activity?.let {activity->
            AppAlertDialog(
                activity,
                getString(R.string.dialog_title),
                getString(R.string.mypage_comment18),
                getString(R.string.app_dialog_action_confirm),
                getString(R.string.app_dialog_action_cancel)

            ).show(
                onClickNegative = {
                    it.dismiss()
                },
                onClickPositive = {
                    viewModelScope.launch {
                        AccountWithdrawalUseCase().parameter2 {
                        }.success {
                            activityFinish()
                            logout {
                                val intent = createStartActivityIntent(view.context)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                view.context.startActivity(intent)
                                activityFinish()
                            }
                        }.fail {

                        }.execute()
                    }
                    it.dismiss()
                }

            )
        }

    }



    fun onLogoutClick(view: View) {
        activity?.let { activity ->
            AppAlertDialog(
                activity,
                getString(R.string.dialog_title),
                getString(R.string.sign_error_2),
                getString(R.string.mypage_comment7),
                getString(R.string.no)

            ).show(
                onClickNegative = {
                },
                onClickPositive = {
                    activityFinish()
                    logout {
                        val intent = createStartActivityIntent(view.context)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        view.context.startActivity(intent)
                        activityFinish()
                    }
                }

            )
        }

    }

    fun getUserInfo() {
        if (UserStore.userInfo == null) {
            viewModelScope.launch {
                val id = UserStore.user?.id
                id?.let {
                    AccountUserInfoUseCase().parameter2 {
                        this.id = it
                    }.success {
                        it?.let {
                            UserStore.userInfoSync(it)
                            nickname.postValue(it.nickname)
                            executeViewBinding()
                        }
                    }.fail {
                    }.execute()
                }

            }
        } else {
            nickname.value = UserStore.userInfo?.nickname
            executeViewBinding()
        }
    }


}
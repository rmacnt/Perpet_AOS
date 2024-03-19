package pet.perpet.equal.presentation.mypage.viewmodel

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pet.perpet.data.nonnull
import pet.perpet.domain.UserStore
import pet.perpet.domain.usecase.account.AccountNickEditUseCase
import pet.perpet.domain.usecase.account.NickNameUseCase
import pet.perpet.equal.R
import pet.perpet.framework.fragment.UseViewModel

class NickNameEditViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    var result: Boolean = false

    val nicknameCheck: MutableLiveData<String> = MutableLiveData()
    val nickError: MutableLiveData<Boolean> = MutableLiveData(false)
    val nickErrorMessage: MutableLiveData<String> = MutableLiveData()

    val nickErrorText: String?
        get() = nickErrorMessage.value

    val nickErrorResult: Boolean?
        get() = nickError.value


    //======================================================================
    // Public Methods
    //======================================================================

    fun onNameTextChange(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        result = false
        nickError.value = false
        nickErrorMessage.value = ""
        nicknameCheck.value = text.toString()
        executeViewBinding()
    }


    fun onNickClick(view: View) {
        when(nicknameCheck.value?.length) {
            0-> {
                nickError.value = true
                nickErrorMessage.value = getString(R.string.mypage_comment11)
                executeViewBinding()
            }
            in 1..10 -> {
                viewModelScope.launch {
                    NickNameUseCase().parameter2 {
                        this.nickname = nicknameCheck.value.nonnull()
                    }.success {
                        it?.let {
                            if((it.data as Boolean).not()) {
                                result = true
                                nickError.value = true
                                nickErrorMessage.value = getString(R.string.mypage_comment17)
                                executeViewBinding()
                            } else {
                                result = false
                                nickError.value = true
                                nickErrorMessage.value = getString(R.string.mypage_comment15)
                                executeViewBinding()
                            }
                        }
                    }.fail {
                    }.execute()
                }
            }
            else-> {
                nickError.value = true
                nickErrorMessage.value = getString(R.string.mypage_comment11)
                executeViewBinding()
            }
        }

    }

    fun onFinishClick(view: View) {
        activity?.let {
            it.finish()
        }
    }

    fun onNickNameEditClick(view: View) {
        when(nicknameCheck.value?.length) {
            0-> {
                nickError.value = true
                nickErrorMessage.value = getString(R.string.mypage_comment11)
                executeViewBinding()
            }
            in 1..10 -> {
                if(result) {
                    viewModelScope.launch {
                        AccountNickEditUseCase().parameter2 {
                            this.nickName = nicknameCheck.value.nonnull()
                        }.success {
                            it?.let {
                                UserStore.userInfoSync(it)
                                Toast.makeText(activity , "닉네임이 변경되었습니다.", Toast.LENGTH_SHORT).show()
                                activity?.finish()
                            }
                        }.fail {
                        }.execute()
                    }
                } else {
                    nickError.value = true
                    nickErrorMessage.value = getString(R.string.mypage_comment12)
                    executeViewBinding()

                }

            }
            else-> {
                nickError.value = true
                nickErrorMessage.value = getString(R.string.mypage_comment11)
                executeViewBinding()
            }
        }

    }


}
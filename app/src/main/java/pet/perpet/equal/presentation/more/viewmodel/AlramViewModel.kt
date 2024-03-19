package pet.perpet.equal.presentation.more.viewmodel

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pet.perpet.data.nonnull
import pet.perpet.domain.UserStore
import pet.perpet.domain.usecase.account.AccountAlarmSettingUseCase
import pet.perpet.domain.usecase.account.AccountUserInfoUseCase
import pet.perpet.framework.fragment.UseViewModel

class AlramViewModel (fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    val info: Boolean?
        get() = UserStore.userInfo?.noti_info.nonnull() == "Y"

    val event: Boolean?
        get() = UserStore.userInfo?.noti_event.nonnull() == "Y"


    //======================================================================
    // Public Methods
    //======================================================================

    fun onFinishClick(view: View) {
        activity?.let {
            it.finish()
        }
    }

    fun onInfoClick(view: View) {
       AccountAlarmSettingUseCase().parameter2 {
           this.type = "info"
           this.value = if(info.nonnull()) "N" else "Y"
       }.success {
           getUserInfo()
       }.fail {
       }.execute()
    }

    fun onEventClick(view: View) {
        AccountAlarmSettingUseCase().parameter2 {
            this.type = "event"
            this.value = if(event.nonnull()) "N" else "Y"
        }.success {
            getUserInfo()
        }.fail {
        }.execute()
    }


    fun getUserInfo() {
        viewModelScope.launch {
            val id = UserStore.user?.id
            id?.let {
                AccountUserInfoUseCase().parameter2 {
                    this.id = it
                }.success {
                    it?.let {
                        UserStore.userInfoSync(it)
                        executeViewBinding()
                    }
                }.fail {
                }.execute()
            }

        }
    }

}
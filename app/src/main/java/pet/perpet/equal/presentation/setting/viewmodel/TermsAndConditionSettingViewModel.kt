package pet.perpet.equal.presentation.setting.viewmodel

import android.text.Spanned
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pet.perpet.data.nonnull
import pet.perpet.domain.UserStore
import pet.perpet.domain.usecase.account.AccountMarketingSettingUseCase
import pet.perpet.domain.usecase.account.AccountUserInfoUseCase
import pet.perpet.equal.R
import pet.perpet.equal.presentation.goWebInfo
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.util.http.HtmlFactory

class TermsAndConditionSettingViewModel (fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    var result:Boolean = false

    val marketing: Boolean?
        get() = UserStore.userInfo?.marketing_agree.nonnull() == "Y"

    val detail: Spanned?
        get() = getString(R.string.setting_comment27)
            .orEmpty().run { HtmlFactory.fromHtml(this) }


    //======================================================================
    // Public Methods
    //======================================================================



    fun onFinishClick(view: View) {
        activity?.let {
            it.finish()
        }
    }

    fun onMarketingDetailClick(view: View) {
        activity?.goWebInfo(getString(R.string.setting_comment17) ,getString(R.string.maketing_url))
    }

    fun onTermClick(view: View) {
        activity?.let {
            when(view.tag) {
                getString(R.string.setting_comment13) -> it.goWebInfo(view.tag as String ,getString(R.string.info_url))
                getString(R.string.setting_comment14) -> it.goWebInfo(view.tag as String ,getString(R.string.term_url))
                getString(R.string.setting_comment15) -> it.goWebInfo(view.tag as String ,getString(R.string.term_url))
            }

        }
    }

    fun onMarketingClick(view: View) {
        AccountMarketingSettingUseCase().parameter2 {
            this.marketingAgree = if(marketing.nonnull()) "N" else "Y"
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
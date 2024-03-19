package pet.perpet.equal.presentation.web.vewmodel

import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.taling.presentation.base.widget.webview.WebClientConfig
import kotlinx.coroutines.launch
import pet.perpet.data.nonnull
import pet.perpet.domain.UserStore
import pet.perpet.domain.model.medical.Medical
import pet.perpet.domain.model.medical.Typeform
import pet.perpet.domain.usecase.medical.MedicalHookUseCase
import pet.perpet.domain.usecase.medical.TypeformInfoUseCase
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.widget.webview.url.WebClientUrl

@WebClientConfig(
    urlLoader = [
        WebClientUrl.Intent
    ]
)
class WebViewViewModel (fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Variables
    //======================================================================

    fun getUrl(result: (Typeform) -> Unit) {
        UserStore.temporaryPet?.let {
            viewModelScope.launch {
                TypeformInfoUseCase().parameter2 {
                    this.user_id = UserStore.user?.id.nonnull()
                    this.pet_id = it.id.nonnull()
                    this.type =  it.type.nonnull()
                }.success {
                    it?.let {
                        result(it)
                    }
                }.fail {
                }.execute()

            }
        }
    }

    fun getUrlTwo(result: () -> Unit) {
        UserStore.temporaryPet?.let {
            viewModelScope.launch {
                MedicalHookUseCase().parameter2 {
                    this.url = "https://equal.qualtrics.com/jfe/form/SV_eQj8rUk9GVaiF7g?user_id=${UserStore.user?.id.nonnull()}&pet_id=${it.id.nonnull()}&target=${it.type.nonnull()}&petname=댕댕&gender=male"
                }.success {
                    it?.let {
                        result()
                    }
                }.fail {
                    result()
                }.execute()
            }
        }
    }
}
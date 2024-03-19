package pet.perpet.equal.presentation.sign.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import pet.perpet.data.nonnull
import pet.perpet.domain.model.main.HomeTag
import pet.perpet.domain.model.profile.Allergy
import pet.perpet.domain.model.profile.Disease
import pet.perpet.equal.presentation.sign.model.PetDisease

class ItemDiseaseCommentViewModel (var model: Disease? = null ) {

    //======================================================================
    // Variables
    //======================================================================

    val name: String?
        get() = model?.name.orEmpty() + " 질환 세부 등록 (선택사항)"

    val comment: String?
        get() = model?.comment


    private var notifyDialog: ((check: Boolean) -> Unit)? = null
    //======================================================================
    // Public Methodcs
    //======================================================================

    fun notifyDialog(value: ((check: Boolean) -> Unit)?) {
        notifyDialog = value
    }

    fun onSearchClick(view: View?) {
        notifyDialog?.let { call ->
            call(true)
        }
    }
}
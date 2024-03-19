package pet.perpet.equal.presentation.health.viewmodel

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pet.perpet.data.nonnull
import pet.perpet.domain.UserStore
import pet.perpet.domain.model.pet.Pet
import pet.perpet.domain.usecase.pet.PetInfoUseCase
import pet.perpet.equal.R
import pet.perpet.equal.presentation.base.factory.FragmentItem
import pet.perpet.equal.presentation.base.ui.AppAlertDialog
import pet.perpet.equal.presentation.finishAll
import pet.perpet.equal.presentation.goExaminationResult
import pet.perpet.equal.presentation.goHome
import pet.perpet.equal.presentation.health.fragment.HealthStartResultPagerFragment
import pet.perpet.framework.fragment.UseViewModel

class HealthInfoResultViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    var result: Boolean = false

    var selectPet: Pet? = null

    var medical: MutableLiveData<String> = MutableLiveData()

    private val fragmentItems = mutableListOf(
        FragmentItem(
            FragmentItem.id_intro_1,
            HealthStartResultPagerFragment::class.java
        ),
        FragmentItem(
            FragmentItem.id_intro_2,
            HealthStartResultPagerFragment::class.java
        ),
        FragmentItem(
            FragmentItem.id_intro_3,
            HealthStartResultPagerFragment::class.java
        )
    )

    val fragmentAdapter by lazy {
        object : FragmentStatePagerAdapter(
            fragment.childFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
            override fun getCount(): Int {
                return fragmentItems.size
            }

            override fun getItem(position: Int): Fragment {
                return fragmentItems[position].createFragment()
            }
        }
    }


    //======================================================================
    // Public Methods
    //======================================================================

    fun onSubmitClick(view: View) {
        selectPet?.let { pet->
            context?.let {
                it.goExaminationResult(
                    pet.name.nonnull(),
                    pet.latest_medical_id.nonnull().toString(),
                    pet.id.toString(),
                    false
                )

                UserStore.setTemporaryClear()
            }
        }
    }

    fun onHomeClick(view: View?) {
        if(result.not()) {
            context?.let {
                AppAlertDialog(
                    it,
                    getString(R.string.dialog_title),
                    getString(R.string.supplement_comment3),
                    getString(R.string.app_dialog_action_confirm)

                ).show(
                    onClickPositive = {
                        it.dismiss()
                    }
                )
            }
        }else {
            activity?.let {
                it.finishAll()
                it.goHome()
            }
        }
    }


    fun getPetInfo(result: (Pet) -> Unit) {
        UserStore.temporaryPet?.let {
            viewModelScope.launch {
                PetInfoUseCase().parameter2 {
                    this.pet_id = it.id.nonnull()
                }.success {
                    it?.let {
                        result(it)
                    }
                }.fail {
                }.execute()

            }
        }
    }

}
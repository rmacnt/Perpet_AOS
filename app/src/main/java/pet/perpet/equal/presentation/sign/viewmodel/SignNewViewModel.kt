package pet.perpet.equal.presentation.sign.viewmodel

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pet.perpet.data.api.request.pet.PetInsertRequest
import pet.perpet.data.api.request.pet.PetProfileRequest
import pet.perpet.data.findIndex
import pet.perpet.data.nonnull
import pet.perpet.domain.UserStore
import pet.perpet.domain.usecase.pet.PetListUseCase
import pet.perpet.domain.usecase.profile.SignUseCase
import pet.perpet.equal.R
import pet.perpet.equal.presentation.base.ui.AppAlertDialog
import pet.perpet.equal.presentation.goHeathInfo
import pet.perpet.equal.presentation.goHeathStart
import pet.perpet.equal.presentation.sign.fragment.SignFragment
import pet.perpet.equal.presentation.sign.model.PetAllergy
import pet.perpet.equal.presentation.sign.model.PetAllergyKnow
import pet.perpet.equal.presentation.sign.model.PetAppetite
import pet.perpet.equal.presentation.sign.model.PetBcs
import pet.perpet.equal.presentation.sign.model.PetBirth
import pet.perpet.equal.presentation.sign.model.PetCatEar
import pet.perpet.equal.presentation.sign.model.PetDisease
import pet.perpet.equal.presentation.sign.model.PetDiseaseTherapy
import pet.perpet.equal.presentation.sign.model.PetEnergy
import pet.perpet.equal.presentation.sign.model.PetFeed
import pet.perpet.equal.presentation.sign.model.PetLiving
import pet.perpet.equal.presentation.sign.model.PetLivingTogether
import pet.perpet.equal.presentation.sign.model.PetName
import pet.perpet.equal.presentation.sign.model.PetNeutering
import pet.perpet.equal.presentation.sign.model.PetSelect
import pet.perpet.equal.presentation.sign.model.PetSex
import pet.perpet.equal.presentation.sign.model.PetSnack
import pet.perpet.equal.presentation.sign.model.PetType
import pet.perpet.equal.presentation.sign.model.PetWalk
import pet.perpet.equal.presentation.sign.model.PetWater
import pet.perpet.equal.presentation.sign.model.PetWeight
import pet.perpet.equal.presentation.sign.viewholder.PetAllergyKnowViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetAllergyViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetAppetiteViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetBcsViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetBirthViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetCatEarViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetDiseaseTherapyViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetDiseaseViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetEnergyViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetFeedViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetLivingTogetherViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetLivingViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetNameViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetNeuteringViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetSelectViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetSexViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetSnackViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetSubmitViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetTypeViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetWalkViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetWaterViewHolder
import pet.perpet.equal.presentation.sign.viewholder.PetWeightViewHolder
import pet.perpet.framework.fragment.RecyclerViewPresenter
import pet.perpet.framework.fragment.ViewHolderSet
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewAdapter
import pet.perpet.framework.widget.recyclerview.RecyclerViewHolder

class SignNewViewModel(fragment: Fragment) : RecyclerViewPresenter(fragment) {

    //======================================================================
    // Variables
    //======================================================================

    val useCase = SignUseCase()

    val items: ArrayList<Any> = arrayListOf()

    val petSelect = PetSelect()
    val petName = PetName()
    val petBirth = PetBirth()
    val petWeight = PetWeight()
    val petType = PetType()
    val petCatEar = PetCatEar()
    val petSex = PetSex()
    val petNeutering = PetNeutering()
    val petBcs = PetBcs()
    val petDisease = PetDisease()
    val petDiseaseTherapy = PetDiseaseTherapy()
    val petEnergy = PetEnergy()
    val petAppetite = PetAppetite()
    val petFeed = PetFeed()
    val petSnack = PetSnack()
    val petWater = PetWater()
    val petAllergy = PetAllergy()
    val petAllergyKnow = PetAllergyKnow()
    val petWalk = PetWalk()
    val petLivingTogether = PetLivingTogether()
    val petLiving = PetLiving()
    val petSubmit = String()

    private var startNetwork = false

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreateViewHolderSet(): ViewHolderSet? {
        return object : ViewHolderSet() {
            override val viewHolderSet: Array<Class<out RecyclerViewHolder<*>>>
                get() = arrayOf(
                    PetSelectViewHolder::class.java,
                    PetNameViewHolder::class.java,
                    PetBirthViewHolder::class.java,
                    PetWeightViewHolder::class.java,
                    PetTypeViewHolder::class.java,
                    PetCatEarViewHolder::class.java,
                    PetSexViewHolder::class.java,
                    PetNeuteringViewHolder::class.java,
                    PetBcsViewHolder::class.java,
                    PetDiseaseViewHolder::class.java,
                    PetDiseaseTherapyViewHolder::class.java,
                    PetEnergyViewHolder::class.java,
                    PetAppetiteViewHolder::class.java,
                    PetFeedViewHolder::class.java,
                    PetSnackViewHolder::class.java,
                    PetWaterViewHolder::class.java,
                    PetAllergyViewHolder::class.java,
                    PetAllergyKnowViewHolder::class.java,
                    PetWalkViewHolder::class.java,
                    PetLivingTogetherViewHolder::class.java,
                    PetLivingViewHolder::class.java,
                    PetSubmitViewHolder::class.java
                )

            override fun asViewType(position: Int): Int {
                return when (items[position]) {
                    petSelect -> PetSelectViewHolder::class.java.hashCode()
                    petName -> PetNameViewHolder::class.java.hashCode()
                    petBirth -> PetBirthViewHolder::class.java.hashCode()
                    petWeight -> PetWeightViewHolder::class.java.hashCode()
                    petType -> PetTypeViewHolder::class.java.hashCode()
                    petCatEar -> PetCatEarViewHolder::class.java.hashCode()
                    petSex -> PetSexViewHolder::class.java.hashCode()
                    petNeutering -> PetNeuteringViewHolder::class.java.hashCode()
                    petBcs -> PetBcsViewHolder::class.java.hashCode()
                    petDisease -> PetDiseaseViewHolder::class.java.hashCode()
                    petDiseaseTherapy -> PetDiseaseTherapyViewHolder::class.java.hashCode()
                    petEnergy -> PetEnergyViewHolder::class.java.hashCode()
                    petAppetite -> PetAppetiteViewHolder::class.java.hashCode()
                    petFeed -> PetFeedViewHolder::class.java.hashCode()
                    petSnack -> PetSnackViewHolder::class.java.hashCode()
                    petWater -> PetWaterViewHolder::class.java.hashCode()
                    petAllergy -> PetAllergyViewHolder::class.java.hashCode()
                    petAllergyKnow -> PetAllergyKnowViewHolder::class.java.hashCode()
                    petWalk -> PetWalkViewHolder::class.java.hashCode()
                    petLivingTogether -> PetLivingTogetherViewHolder::class.java.hashCode()
                    petLiving -> PetLivingViewHolder::class.java.hashCode()
                    petSubmit -> PetSubmitViewHolder::class.java.hashCode()
                    else -> PetSelectViewHolder::class.java.hashCode()
                }
            }
        }
    }

    override fun onCreateProvider(): BaseRecyclerViewAdapter.Provider {
        return object : BaseRecyclerViewAdapter.Provider {
            override fun getItemHeaderCount(): Int {
                return 0
            }

            override fun getSupportItemCount(): Int {
                return items.size
            }

            override fun getSupportItemViewType(position: Int): Int {
                return viewHolderSet.asViewType(position)
            }

            override fun getSupportItem(viewType: Int, position: Int): Any? {
                return items[position]
            }
        }
    }

    //======================================================================
    // Public Methods
    //======================================================================

    fun onBackClick(view: View?) {
        activity?.let { activity ->
            AppAlertDialog(
                activity,
                getString(R.string.dialog_title),
                getString(R.string.sign_error_1),
                getString(R.string.sign_comment_129),
                getString(R.string.sign_comment_130)

            ).show(
                onClickNegative = {
                    it.dismiss()
                },
                onClickPositive = {
                    activity.finish()
                }

            )
        }
    }

    fun removeItemCallback(
        callback: (index: Int) -> Unit,
        param: (Any) -> Unit,
        signFragment: SignFragment,
    ) {

        items.findIndex { item ->
            item is PetAllergyKnow
        }.takeIf {
            it > -1
        }?.run {
            items.removeAt(this)
            callback(this)
        }
    }

    fun onSubmitClick(callback: (value: Int) -> Unit) {

        Log.d("CHECK", "값확인${petLiving.mainActPlaceCode}")

        var position = 0
        var error = false
        if (petName.petName.isEmpty()) {
            error = true
            petName.error = true
            position = 1
        }


        if (petWeight.petWeight?.isEmpty() == true) {
            error = true
            petWeight.error = true

            if(position == 0) {
                position = 2
            }
        }

        if (petType.petBreedId == 0) {
            error = true
            petType.error = true
            if(position == 0) {
                position = 3
            }
        }

        if (petNeutering.neutralizationCode == -1) {
            error = true
            petNeutering.error = true
            if(position == 0) {
                position = 5
            }
        }

        if (petBcs.petBcsCode == -1) {
            error = true
            petBcs.error = true
            if(position == 0) {
                position = 6
            }
        }

        if (petDisease.petDisease == 0) {
            if (petDisease.diseaseId == null || petDisease.diseaseId?.isEmpty() == true) {
                error = true
                petDisease.error = true
                if(position == 0) {
                    position = 7
                }
            }

            if (petDiseaseTherapy.diseaseTreatCode == -1) {
                error = true
                petDiseaseTherapy.error = true
                if(position == 0) {
                    position = 8
                }
            }
        }

        if(petEnergy.conditionsCode == -1) {
            error = true
            petEnergy.error = true
            if(position == 0) {
                position = 9
            }
        }

        if(petAppetite.appetiteChangeCode == -1) {
            error = true
            petAppetite.error = true
            if(position == 0) {
                position = 10
            }
        }

        if(petFeed.feedAmountCode == -1) {
            error = true
            petFeed.error = true
            if(position == 0) {
                position = 11
            }
        }

        if(petWater.drinkingAmountCode == -1) {
            error = true
            petWater.error = true
            if(position == 0) {
                position = 12
            }
        }

        if (petAllergy.petAllegi == 0) {
            if (petAllergy.allergyId == null || petAllergy.allergyId?.isEmpty() == true) {
                petAllergy.error = true
                error = true
                if(position == 0) {
                    position = 13
                }
            }

            if (petAllergyKnow.howToKnowAllergyCode == -1) {
                petAllergyKnow.error = true
                error = true
                if(position == 0) {
                    position = 14
                }
            }
        }

        if (petSelect.petSelect == "cat") {
            if (petCatEar.petEar?.isEmpty() == true) {
                petCatEar.error = true
                error = true
                if(position == 0) {
                    position = 4
                }
            }

            if (petLivingTogether.relationshipCode == -1) {
                petLivingTogether.error = true
                error = true
                if(position == 0) {
                    position = 15
                }
            }
        } else {
            if (petWalk.walkCode == -1) {
                petWalk.error = true
                error = true
                if(position == 0) {
                    position = 16
                }
            }

            if(petLiving.mainActPlaceCode == -1) {
                petLiving.error = true
                error = true
                if(position == 0) {
                    position = 17
                }
            }

            if(petLiving.mainActPlaceCode == 4) {
                if(petLiving.mainActPlaceEtc?.isEmpty() == true) {
                    petLiving.error = true
                    petLiving.errorMessage = getString(R.string.sign_comment_128)
                    error = true
                    if(position == 0) {
                        position = 17
                    }
                }
            }
        }

        if(error.not() && startNetwork.not()) {
            startNetwork = true

            var diseaseId = ""
            var diseaseResult = ""
            petDisease.diseaseId?.let {
                if(petDisease.diseaseId.nonnull().isNotEmpty()) {
                    if(petDisease.diseaseSub.size > 0) {
                        petDisease.diseaseSub.forEach {
                            if(it.value.isNotEmpty()) {
                                diseaseId = if(diseaseId.isNotEmpty()) {
                                    diseaseId + "," +it.value
                                } else {
                                    it.value
                                }
                            }
                        }
                    }
                }else {
                    diseaseId = ""
                }
            }
            diseaseResult = petDisease.diseaseId?.let {
                if(it.isNotEmpty()) {
                    if(diseaseId.isNotEmpty()) {
                        "$it,$diseaseId"
                    }else {
                        it
                    }
                } else {
                    ""
                }
            } ?: run {
                ""
            }
            viewModelScope.launch {
                useCase.petInsert(
                    PetInsertRequest(
                        petSelect.petSelect,
                        petName.petName,
                        petType.petBreedId,
                        petSex.petSex,
                        "${petBirth.petBirthYear}-${petBirth.petBirthMonth?.padStart(2, '0')}",
                        PetProfileRequest(
                            petWeight.petWeight?.toDouble(),
                            petCatEar.petEar ?: "",
                            petNeutering.neutralizationCode,
                            petBcs.petBcsCode,
                            diseaseResult,
                            petDiseaseTherapy.diseaseTreatCode,
                            petEnergy.conditionsCode,
                            petAppetite.appetiteChangeCode,
                            petFeed.feedAmountCode,
                            if(petSnack.snack == 0) "Y" else "N",
                            petWater.drinkingAmountCode,
                            petAllergy.allergyId ?: "",
                            petAllergyKnow.howToKnowAllergyCode,
                            petWalk.walkCode ?: -1,
                            petLivingTogether.relationshipCode ?: -1,
                            petLiving.mainActPlaceCode ?: -1
                        )
                    )
                ).success { data ->
                    data?.let {
                        UserStore.setTemporaryPet(data)
                    }
                    getPetList()
                }.fail {
                    startNetwork = false
                }.execute()
            }
        } else {
            callback(position)
        }

    }

    fun getPetList() {
        PetListUseCase().parameter2 {
        }.success {
            it?.let {
                it.content?.let { data ->
                    if (data.size == 1) {
                        context?.goHeathInfo()
                        activity?.finish()
                    } else {
                        context?.goHeathStart()
                        activity?.finish()
                    }
                }
            } ?: run {
                context?.goHeathInfo()
                activity?.finish()
            }
        }.fail {
            context?.goHeathStart()
            activity?.finish()
        }.execute()

        startNetwork = false
    }
}
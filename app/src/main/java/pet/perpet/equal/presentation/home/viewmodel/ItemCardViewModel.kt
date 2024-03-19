package pet.perpet.equal.presentation.home.viewmodel

import android.view.View
import pet.perpet.data.nonnull
import pet.perpet.domain.UserStore
import pet.perpet.domain.model.main.MainCard
import pet.perpet.domain.model.medical.Medical
import pet.perpet.domain.usecase.medical.MedicalResultUseCase
import pet.perpet.equal.presentation.goExaminationResult
import pet.perpet.equal.presentation.goHeathStart
import pet.perpet.equal.presentation.goIntakeCheck
import pet.perpet.equal.presentation.goSupplementPackage

class ItemCardViewModel (
    var model: MainCard? = null
) {

    val top: String?
        get() = model?.top

    val summary: String?
        get() = model?.summary

    val imageDrawableResult: Boolean?
        get() = model?.imageDrawable != 0

    val typeCard: String?
        get() = if(model?.imageDrawable != 0) model?.type else ""

    private var notify: ((value: MainCard) -> Unit)? = null


    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: ((value: MainCard) -> Unit)?) {
        notify = value
    }

    fun onImageClick(view: View) {
        model?.let {
            notify?.let {call->
                call(it)
            }
        }
    }

    fun onItemClick(view: View) {
        when (model?.cardType) {
            1 , 2  -> {
                UserStore.mainPet?.let {
                    UserStore.setTemporaryPet(it)
                    view.context.goHeathStart()
                }
            }

            3 -> {
                getData(UserStore.mainPet?.latest_medical_id.nonnull()) { data ->
                    view.context?.goSupplementPackage(data,  model?.petId?.toString().nonnull() ,  UserStore.mainPet?.name.toString().nonnull() , false)
                }
            }

            4 -> {
                view.context.goExaminationResult(
                    UserStore.mainPet?.name.nonnull(),
                    UserStore.mainPet?.latest_medical_id.nonnull().toString(),
                    model?.petId.toString(),
                    UserStore.mainPet?.latest_order_id != null
                )
            }

            5 -> {
                getData(UserStore.mainPet?.latest_medical_id.nonnull()) { data ->
                    view.context?.goSupplementPackage(data,  model?.petId?.toString().nonnull() ,  UserStore.mainPet?.name.toString().nonnull() , true)
                }
            }
            6 -> {
                view.context.goIntakeCheck()
            }

        }
    }

    fun getData(petId: Int, result: (Medical) -> Unit) {
        MedicalResultUseCase().parameter2 {
            this.id = petId
        }.success {
            it?.let {
                result(it)
            }
        }.fail {
        }.execute()
    }

}
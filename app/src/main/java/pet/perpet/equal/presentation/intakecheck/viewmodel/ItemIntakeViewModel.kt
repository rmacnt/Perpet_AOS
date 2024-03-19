package pet.perpet.equal.presentation.intakecheck.viewmodel

import android.view.View
import pet.perpet.data.nonnull
import pet.perpet.domain.UserStore
import pet.perpet.domain.model.medical.Medical
import pet.perpet.domain.model.pet.Pet
import pet.perpet.domain.usecase.medical.MedicalResultUseCase
import pet.perpet.equal.presentation.goHeathStart
import pet.perpet.equal.presentation.goSubscribe

class ItemIntakeViewModel(var model: Pet? = null , var type: Boolean? = false) {

    //======================================================================
    // Variables
    //======================================================================

    val name: String?
        get() = if(type.nonnull()) {
            model?.name + "영양제 패키지"
        } else {
            model?.name
        }

    private var notify: (() -> Unit)? = null

    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: (() -> Unit)?) {
        notify = value
    }

    fun onClick(view: View) {
        model?.let {
            if(type.nonnull()) {
                getData(model?.latest_medical_id.nonnull()) { data ->
                    view.context.goSubscribe(
                        data,
                        null,
                        model?.id.toString(),
                        model?.name.nonnull()
                    )
                }
            } else {
                UserStore.setTemporaryPet(it)
                view.context.goHeathStart()
            }

            notify?.let {call->
                call()
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
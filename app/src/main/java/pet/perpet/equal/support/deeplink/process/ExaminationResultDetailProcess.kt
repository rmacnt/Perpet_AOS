package pet.perpet.equal.support.deeplink.process

import android.content.Context
import pet.perpet.data.nonnull
import pet.perpet.domain.UserStore
import pet.perpet.domain.usecase.pet.PetInfoUseCase
import pet.perpet.equal.presentation.getSubscribeDetail
import pet.perpet.equal.presentation.goExaminationResult
import pet.perpet.equal.presentation.goHeathStart
import pet.perpet.equal.support.deeplink.model.BaseArgument

class ExaminationResultDetailProcess(body: BaseArgument) : DeepLinkProcess(body) {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun observable(context: Context) {

        if (body.petId.isNotEmpty()) {
            PetInfoUseCase().parameter2 {
                this.pet_id = body.petId.nonnull().toInt()
            }.success {
                it?.let {
                    if (body.examinationId.isNotEmpty()) {
                        context.goExaminationResult(
                            it.name.nonnull(),
                            body.examinationId,
                            body.petId,
                            it.latest_order_id != null
                        )
                    }
                    finish(context)
                }
            }.fail {
                finish(context)
            }.execute()
        }


        finish(context)
    }

}
package pet.perpet.equal.support.push.process

import android.content.Context
import android.net.Uri
import pet.perpet.data.nonnull
import pet.perpet.domain.usecase.pet.PetInfoUseCase
import pet.perpet.equal.presentation.goExaminationResult
import pet.perpet.equal.support.push.model.MessageBody
import pet.perpet.equal.support.push.presentation.PushProcess

class PushExaminationResultDetailProcess(body: MessageBody) : PushProcess(body) {

    override fun observable(context: Context) {
        val petId = Uri.parse(body.link.orEmpty()).getQueryParameter("petId")
        val examinationId = Uri.parse(body.link.orEmpty()).getQueryParameter("examinationId")
        if (petId.nonnull().isNotEmpty()) {
            PetInfoUseCase().parameter2 {
                this.pet_id = petId.nonnull().toInt()
            }.success {
                it?.let {
                    if (examinationId.nonnull().isNotEmpty()) {
                        context.goExaminationResult(
                            it.name.nonnull(),
                            examinationId.nonnull(),
                            petId.nonnull(),
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
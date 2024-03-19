package pet.perpet.equal.support.push.process

import android.content.Context
import android.net.Uri
import pet.perpet.domain.UserStore
import pet.perpet.domain.usecase.pet.PetInfoUseCase
import pet.perpet.equal.presentation.goHeathStart
import pet.perpet.equal.support.push.model.MessageBody
import pet.perpet.equal.support.push.presentation.PushProcess

class PushExaminationDetailsProcess(body: MessageBody) : PushProcess(body) {

    override fun observable(context: Context) {

        val petId = Uri.parse(body.link.orEmpty()).getQueryParameter("petId")
        petId?.let {
            PetInfoUseCase().parameter2 {
                this.pet_id = it.toInt()
            }.success {
                it?.let {
                    UserStore.setTemporaryPet(it)
                    context.goHeathStart()
                    finish(context)
                }
            }.fail {
                finish(context)
            }.execute()
        }

    }
}
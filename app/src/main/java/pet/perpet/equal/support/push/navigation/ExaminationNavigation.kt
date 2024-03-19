package pet.perpet.equal.support.push.navigation

import android.content.Context
import android.net.Uri
import pet.perpet.domain.UserStore
import pet.perpet.domain.model.notification.PushList
import pet.perpet.domain.usecase.pet.PetInfoUseCase
import pet.perpet.equal.presentation.goHeathStart

class ExaminationNavigation  (args: PushList) : Navigation(args) {

    override fun isValid(args: PushList): Boolean {
        return true
    }

    override fun go(context: Context) {
        val petId = Uri.parse(args.link.orEmpty()).getQueryParameter("petId")
        petId?.let {
            PetInfoUseCase().parameter2 {
                this.pet_id = it.toInt()
            }.success {
                it?.let {
                    UserStore.setTemporaryPet(it)
                    context.goHeathStart()
                }
            }.fail {
            }.execute()
        }
    }
}
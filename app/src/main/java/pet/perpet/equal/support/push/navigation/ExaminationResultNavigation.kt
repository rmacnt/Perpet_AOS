package pet.perpet.equal.support.push.navigation

import android.content.Context
import android.net.Uri
import pet.perpet.data.nonnull
import pet.perpet.domain.model.notification.PushList
import pet.perpet.domain.usecase.pet.PetInfoUseCase
import pet.perpet.equal.presentation.goExaminationResult

class ExaminationResultNavigation (args: PushList) : Navigation(args) {

    override fun isValid(args: PushList): Boolean {
        return true
    }

    override fun go(context: Context) {

        val petId = Uri.parse(args.link.orEmpty()).getQueryParameter("petId")
        val examinationId = Uri.parse(args.link.orEmpty()).getQueryParameter("examinationId")
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
                }
            }.fail {
            }.execute()
        }
    }
}
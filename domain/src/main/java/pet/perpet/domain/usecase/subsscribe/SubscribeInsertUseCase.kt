package pet.perpet.domain.usecase.subsscribe

import pet.perpet.data.repository.subscribe.SubscribeInsertRepository
import pet.perpet.domain.usecase.UseCase

class SubscribeInsertUseCase :
    UseCase<SubscribeInsertRepository, SubscribeInsertRepository.Parameter, Any, Any>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: Any?): Any? {
        return raw
    }
}
package pet.perpet.domain.usecase.card

import pet.perpet.data.repository.card.CardInsertRepository
import pet.perpet.domain.usecase.UseCase

class CardInsertUseCase :
    UseCase<CardInsertRepository, CardInsertRepository.Parameter, Any, Any>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: Any?): Any? {
        return raw
    }
}
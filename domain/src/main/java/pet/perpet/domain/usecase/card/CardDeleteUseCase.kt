package pet.perpet.domain.usecase.card

import pet.perpet.data.repository.card.CardDeleteRepository
import pet.perpet.domain.usecase.UseCase

class CardDeleteUseCase :
    UseCase<CardDeleteRepository, CardDeleteRepository.Parameter, Any, Any>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: Any?): Any? {
        return raw
    }
}
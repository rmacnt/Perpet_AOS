package pet.perpet.domain.usecase.card

import pet.perpet.data.api.entity.card.CardChangeEntity
import pet.perpet.data.repository.card.CardChangeRepository
import pet.perpet.domain.gson
import pet.perpet.domain.model.card.CardChange
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class CardChangeUseCase :
    UseCase<CardChangeRepository, CardChangeRepository.Parameter, CardChange, CardChangeEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: CardChangeEntity?): CardChange? {
        return raw?.toJson()?.let {
            gson.fromJson(it, CardChange::class.java)
        }
    }
}
package pet.perpet.domain.usecase.pet

import pet.perpet.data.api.entity.pet.PetEntity
import pet.perpet.data.repository.pet.PetInsertRepository
import pet.perpet.domain.gson
import pet.perpet.domain.model.pet.Pet
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class PetInsertUseCase :
    UseCase<PetInsertRepository, PetInsertRepository.Parameter, Pet, PetEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: PetEntity?): Pet? {
        return raw?.toJson()?.let {
            gson.fromJson(it, Pet::class.java)
        }
    }
}
package pet.perpet.domain.usecase.profile

import pet.perpet.data.api.entity.profile.BreedEntity
import pet.perpet.data.repository.profile.BreedSetRepository
import pet.perpet.domain.gson
import pet.perpet.domain.model.profile.Breed
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class BreedSetUseCase :
    UseCase<BreedSetRepository, BreedSetRepository.Parameter, Breed, BreedEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: BreedEntity?): Breed? {
        return raw?.toJson()?.let {
            gson.fromJson(it, Breed::class.java)
        }
    }
}
package pet.perpet.domain.usecase.profile

import pet.perpet.data.api.entity.profile.AllergyEntity
import pet.perpet.data.repository.profile.AllergySetRepository
import pet.perpet.domain.gson
import pet.perpet.domain.model.profile.Allergy
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class AllergySetUseCase :
    UseCase<AllergySetRepository, AllergySetRepository.Parameter, Allergy, AllergyEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: AllergyEntity?): Allergy? {
        return raw?.toJson()?.let {
            gson.fromJson(it, Allergy::class.java)
        }
    }
}
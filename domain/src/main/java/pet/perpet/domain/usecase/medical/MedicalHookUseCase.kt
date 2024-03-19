package pet.perpet.domain.usecase.medical

import pet.perpet.data.api.entity.medical.MedicalEntity
import pet.perpet.data.repository.medical.MedicalHookRepository
import pet.perpet.domain.gson
import pet.perpet.domain.model.medical.Medical
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class MedicalHookUseCase :
    UseCase<MedicalHookRepository, MedicalHookRepository.Parameter, Medical, MedicalEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: MedicalEntity?): Medical? {
        return raw?.toJson()?.let {
            gson.fromJson(it, Medical::class.java)
        }
    }
}
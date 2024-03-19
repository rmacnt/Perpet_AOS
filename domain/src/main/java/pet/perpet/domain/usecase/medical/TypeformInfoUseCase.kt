package pet.perpet.domain.usecase.medical

import pet.perpet.data.api.entity.medical.TypeformEntity
import pet.perpet.data.repository.medical.TypeformInfoRepository
import pet.perpet.domain.gson
import pet.perpet.domain.model.medical.Typeform
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class TypeformInfoUseCase :
    UseCase<TypeformInfoRepository, TypeformInfoRepository.Parameter, Typeform, TypeformEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: TypeformEntity?): Typeform? {
        return raw?.toJson()?.let {
            gson.fromJson(it, Typeform::class.java)
        }
    }
}
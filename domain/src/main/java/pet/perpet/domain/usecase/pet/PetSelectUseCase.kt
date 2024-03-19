package pet.perpet.domain.usecase.pet

import pet.perpet.data.repository.pet.PetSelectRepository
import pet.perpet.domain.usecase.UseCase

class PetSelectUseCase  :
    UseCase<PetSelectRepository, PetSelectRepository.Parameter, Any, Any>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: Any?): Any? {
        return raw
    }
}
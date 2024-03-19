package pet.perpet.domain.usecase.address

import pet.perpet.data.repository.address.AddressDeleteRepository
import pet.perpet.domain.usecase.UseCase

class AddressDeleteUseCase  :
    UseCase<AddressDeleteRepository, AddressDeleteRepository.Parameter, Any, Any>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: Any?): Any? {
        return raw
    }
}
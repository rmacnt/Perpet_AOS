package pet.perpet.domain.usecase.address

import pet.perpet.data.api.entity.address.AddressEntity
import pet.perpet.data.repository.address.AddressListRepository
import pet.perpet.domain.gson
import pet.perpet.domain.model.address.Address
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class AddressListUseCase :
    UseCase<AddressListRepository, AddressListRepository.Parameter, Address, AddressEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: AddressEntity?): Address? {
        return raw?.toJson()?.let {
            gson.fromJson(it, Address::class.java)
        }
    }
}
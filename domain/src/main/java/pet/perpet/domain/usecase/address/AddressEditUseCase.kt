package pet.perpet.domain.usecase.address

import pet.perpet.data.api.entity.address.ListAddressEntity
import pet.perpet.data.repository.address.AddressEditRepository
import pet.perpet.domain.gson
import pet.perpet.domain.model.address.ListAddress
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class AddressEditUseCase :
    UseCase<AddressEditRepository, AddressEditRepository.Parameter, ListAddress, ListAddressEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: ListAddressEntity?): ListAddress? {
        return raw?.toJson()?.let {
            gson.fromJson(it, ListAddress::class.java)
        }
    }
}
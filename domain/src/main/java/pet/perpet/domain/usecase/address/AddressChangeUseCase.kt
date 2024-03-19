package pet.perpet.domain.usecase.address

import pet.perpet.data.api.entity.order.OrderContentEntity
import pet.perpet.data.repository.address.AddressChangeRepository
import pet.perpet.domain.gson
import pet.perpet.domain.model.order.OrderContent
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class AddressChangeUseCase  :
    UseCase<AddressChangeRepository, AddressChangeRepository.Parameter, OrderContent, OrderContentEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: OrderContentEntity?): OrderContent? {
        return raw?.toJson()?.let {
            gson.fromJson(it, OrderContent::class.java)
        }
    }
}
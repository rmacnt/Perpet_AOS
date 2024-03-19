package pet.perpet.domain.usecase.order

import pet.perpet.data.api.entity.order.InventoryEntity
import pet.perpet.data.repository.order.OrderAddressChangeRepository
import pet.perpet.domain.gson
import pet.perpet.domain.model.order.Inventory
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class OrderAddressChangeUseCase :
    UseCase<OrderAddressChangeRepository, OrderAddressChangeRepository.Parameter, Inventory, InventoryEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: InventoryEntity?): Inventory? {
        return raw?.toJson()?.let {
            gson.fromJson(it, Inventory::class.java)
        }
    }
}
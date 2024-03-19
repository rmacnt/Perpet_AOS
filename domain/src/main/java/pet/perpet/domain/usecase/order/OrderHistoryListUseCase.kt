package pet.perpet.domain.usecase.order

import com.google.gson.reflect.TypeToken
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.order.InventoryEntity
import pet.perpet.data.repository.order.OrderListRepository
import pet.perpet.domain.GsonLoader
import pet.perpet.domain.model.order.Inventory
import pet.perpet.domain.model.page.PageContents
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class OrderHistoryListUseCase :
    UseCase<OrderListRepository, OrderListRepository.Parameter, PageContents<Inventory>, PageContentsEntity<InventoryEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: PageContentsEntity<InventoryEntity>?): PageContents<Inventory>? {
        return GsonLoader.gson.fromJson(
            raw.toJson(),
            object : TypeToken<PageContents<Inventory>>() {}.type
        )
    }

}
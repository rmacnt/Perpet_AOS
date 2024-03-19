package pet.perpet.domain.usecase.order

import pet.perpet.data.api.entity.order.OrderContentEntity
import pet.perpet.data.repository.order.OrderNowDetailRepository
import pet.perpet.domain.gson
import pet.perpet.domain.model.order.OrderContent
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class OrderNowDetailUseCase :
    UseCase<OrderNowDetailRepository, OrderNowDetailRepository.Parameter, OrderContent, OrderContentEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: OrderContentEntity?): OrderContent? {
        return raw?.toJson()?.let {
            gson.fromJson(it, OrderContent::class.java)
        }
    }
}
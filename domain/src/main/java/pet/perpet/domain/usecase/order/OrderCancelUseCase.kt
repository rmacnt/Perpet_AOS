package pet.perpet.domain.usecase.order

import pet.perpet.data.repository.order.OrderCancelRepository
import pet.perpet.domain.usecase.UseCase

class OrderCancelUseCase  :
    UseCase<OrderCancelRepository, OrderCancelRepository.Parameter, Any, Any>() {
}
package pet.perpet.domain.usecase.order

import pet.perpet.data.repository.order.OrderMoveBackRepository
import pet.perpet.domain.usecase.UseCase

class OrderMoveBackUseCase :
    UseCase<OrderMoveBackRepository, OrderMoveBackRepository.Parameter, Any, Any>() {
}
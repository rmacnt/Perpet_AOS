package pet.perpet.domain.usecase.order

import pet.perpet.data.repository.order.OrderNowCancelRepository
import pet.perpet.domain.usecase.UseCase

class OrderNowCancelUseCase  :
    UseCase<OrderNowCancelRepository, OrderNowCancelRepository.Parameter, Any, Any>() {
}
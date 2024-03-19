package pet.perpet.equal.presentation.more.model

import pet.perpet.domain.model.card.OrdersInfo

data class OrderInfo(
    var name: String?,
    var type: String?,
    var orderInfo: OrdersInfo? = null,
    var petName: String?
)

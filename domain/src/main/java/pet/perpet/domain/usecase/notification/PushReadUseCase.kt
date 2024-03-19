package pet.perpet.domain.usecase.notification

import pet.perpet.data.repository.notification.PushReadRepository
import pet.perpet.domain.usecase.UseCase

class PushReadUseCase :
    UseCase<PushReadRepository, PushReadRepository.Parameter, Any, Any>() {
}
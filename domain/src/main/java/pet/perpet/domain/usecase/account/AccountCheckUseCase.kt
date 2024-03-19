package pet.perpet.domain.usecase.account

import pet.perpet.data.api.entity.response.DefaultResponse
import pet.perpet.data.repository.account.AccountCheckRepository
import pet.perpet.domain.usecase.UseCase

class AccountCheckUseCase :
    UseCase<AccountCheckRepository, AccountCheckRepository.Parameter, Any, Any>() {
}
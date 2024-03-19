package pet.perpet.domain.usecase.account

import pet.perpet.data.repository.account.AccountPushTokenRepository
import pet.perpet.domain.usecase.UseCase

class AccountPushTokenUseCase :
    UseCase<AccountPushTokenRepository, AccountPushTokenRepository.Parameter, Any, Any>() {
}
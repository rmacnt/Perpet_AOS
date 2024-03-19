package pet.perpet.domain.usecase.account

import pet.perpet.data.repository.account.AccountWithdrawalRepository
import pet.perpet.domain.usecase.UseCase

class AccountWithdrawalUseCase :
    UseCase<AccountWithdrawalRepository, AccountWithdrawalRepository.Parameter, Any, Any>() {
}
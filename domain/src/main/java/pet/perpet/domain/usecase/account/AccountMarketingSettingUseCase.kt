package pet.perpet.domain.usecase.account

import pet.perpet.data.repository.account.AccountMarketingSettingRepository
import pet.perpet.domain.usecase.UseCase

class AccountMarketingSettingUseCase :
    UseCase<AccountMarketingSettingRepository, AccountMarketingSettingRepository.Parameter, Any, Any>() {
}
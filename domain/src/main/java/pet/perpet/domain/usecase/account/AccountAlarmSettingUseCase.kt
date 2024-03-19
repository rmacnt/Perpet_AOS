package pet.perpet.domain.usecase.account

import pet.perpet.data.repository.account.AccountAlarmSettingRepository
import pet.perpet.domain.usecase.UseCase

class AccountAlarmSettingUseCase :
    UseCase<AccountAlarmSettingRepository, AccountAlarmSettingRepository.Parameter, Any, Any>() {
}
package pet.perpet.domain.usecase.account

import pet.perpet.data.api.entity.account.UserLoginEntity
import pet.perpet.data.repository.account.AccountLoginRepository
import pet.perpet.domain.gson
import pet.perpet.domain.model.account.UserLogin
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class AccountLoginUseCase :
    UseCase<AccountLoginRepository, AccountLoginRepository.Parameter, UserLogin, UserLoginEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: UserLoginEntity?): UserLogin? {
        return raw?.toJson()?.let {
            gson.fromJson(it, UserLogin::class.java)
        }
    }
}
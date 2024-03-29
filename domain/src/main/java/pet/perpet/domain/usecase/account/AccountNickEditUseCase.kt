package pet.perpet.domain.usecase.account

import pet.perpet.data.api.entity.account.UserEntity
import pet.perpet.data.repository.account.AccountNickEditRepository
import pet.perpet.domain.gson
import pet.perpet.domain.model.account.User
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class AccountNickEditUseCase :
    UseCase<AccountNickEditRepository, AccountNickEditRepository.Parameter, User, UserEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: UserEntity?): User? {
        return raw?.toJson()?.let {
            gson.fromJson(it, User::class.java)
        }
    }
}
package pet.perpet.domain.usecase.account

import pet.perpet.data.repository.account.AuthenticationRepository
import pet.perpet.domain.gson
import pet.perpet.domain.model.account.User
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class AuthenticationUseCase :
    UseCase< AuthenticationRepository, AuthenticationRepository.Parameter, Any, Any>() {
}
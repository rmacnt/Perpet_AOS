package pet.perpet.domain.usecase.account

import pet.perpet.data.repository.account.AuthenticationSendSmsRepository
import pet.perpet.domain.usecase.UseCase

class AuthenticationSendSmsUseCase :
    UseCase<AuthenticationSendSmsRepository, AuthenticationSendSmsRepository.Parameter, Any, Any>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: Any?): Any? {
        return raw
    }

}
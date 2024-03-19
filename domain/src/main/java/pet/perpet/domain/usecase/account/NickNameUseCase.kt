package pet.perpet.domain.usecase.account

import pet.perpet.data.api.entity.response.BaseObjectResponse
import pet.perpet.data.repository.account.NickNameEditCheckRepository
import pet.perpet.domain.usecase.UseCase

class NickNameUseCase :
    UseCase<NickNameEditCheckRepository, NickNameEditCheckRepository.Parameter, BaseObjectResponse<Any>, Any>() {

}
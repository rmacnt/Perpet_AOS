package pet.perpet.domain.usecase.account

import pet.perpet.data.api.entity.account.TokenEntity
import pet.perpet.data.repository.account.TokenRepository
import pet.perpet.domain.gson
import pet.perpet.domain.model.account.Token
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase


class TokenUseCase : UseCase<TokenRepository, TokenUseCase.Parameter, Token, TokenEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: TokenEntity?): Token? {
        return gson.fromJson(raw.toJson(), Token::class.java)
    }

    //======================================================================
    // Parameter
    //======================================================================

    class Parameter : TokenRepository.Parameter()
}
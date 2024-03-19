package pet.perpet.domain

import pet.perpet.data.api.entity.account.TokenEntity
import pet.perpet.data.repository.account.TokenRepository
import pet.perpet.data.repository.base.datasource.CashDataSource
import pet.perpet.domain.model.account.Term
import pet.perpet.domain.model.account.Token
import pet.perpet.domain.usecase.UseCase
import pet.perpet.domain.usecase.account.TokenUseCase


object TokenStore {

    //======================================================================
    // Variables
    //======================================================================

    private val pushToken: CashDataSource<String> by lazy {
        object : CashDataSource<String>() {
            override val key: String
                get() = "deviceToken"

        }
    }

    private val accessTokenDataSource: CashDataSource<Token> by lazy {
        object : CashDataSource<Token>() {
            override val key: String
                get() = "accessTokenDataSource"
        }
    }

    private val accessTermDataSource: CashDataSource<Term> by lazy {
        object : CashDataSource<Term>() {
            override val key: String
                get() = "accessTermDataSource"
        }
    }

    val refreshToken: String?
        get() = accessTokenDataSource.get()?.refreshToken

    val accessToken: String?
        get() = accessTokenDataSource.get()?.accessToken

    val deviceToken: String?
        get() = pushToken.get()

    val term: Term?
        get() = accessTermDataSource.get()

    val isUseToken: Boolean
        get() = !refreshToken.isNullOrEmpty() && !accessToken.isNullOrEmpty()

    //======================================================================
    // Public Methods
    //======================================================================

    fun saveDeviceToken(token: String?) {
        this.pushToken.save(token)
    }

    fun updateAccessToken(token: String) {
        val t = accessTokenDataSource.get()
        t?.let { model ->
            accessTokenDataSource.clear()
            model.accessToken = token
            accessTokenDataSource.save(model)
        }
    }

    fun saveToken(entity: TokenEntity?) {
        TokenUseCase().saveCash(entity)
        accessTokenDataSource.save(TokenUseCase().local().sync())
    }
    fun saveTerm(entity: Term?) {
        accessTermDataSource.save(entity)
    }


    fun syncResetDeviceToken() {
        this.pushToken.clear()
    }

    fun syncDeviceToken(token: String?) {
        this.pushToken.clear()
        this.pushToken.save(token)
    }


    @Deprecated("not")
    fun syncToken(param: TokenUseCase.Parameter): Token? {
        val t = createTokenUseCase(param).sync()
        accessTokenDataSource.save(t)
        return t
    }

    fun clear() {
        TokenUseCase().clearCash()
        accessTokenDataSource.clear()
    }

    //======================================================================
    // Private Methods
    //======================================================================

    private fun createTokenUseCase(param: TokenUseCase.Parameter): UseCase<TokenRepository, TokenUseCase.Parameter, Token, TokenEntity> {
        return TokenUseCase().parameter(param)
    }
}
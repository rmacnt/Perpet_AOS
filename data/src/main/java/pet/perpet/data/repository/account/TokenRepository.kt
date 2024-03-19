package pet.perpet.data.repository.account

import pet.perpet.data.DataConfig
import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.account.TokenEntity
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.CashDataSource
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class TokenRepository : Repository<TokenRepository.Parameter, TokenEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createCashDataSource(): CashDataSource<TokenEntity>? {
        return object : CashDataSource<TokenEntity>() {
            override val key: String
                get() = TokenRepository::class.java.name

        }
    }

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object : RemoteDataSource<Parameter, TokenEntity, TokenEntity>() {

            override fun toObject(raw: TokenEntity?): TokenEntity? {
                return raw
            }

            override fun fetchApi(param: Parameter?): ApiCall<TokenEntity> {
                return Client.account.token(
                    accessToken = DataConfig.getProvider().clintId,
                    refreshToken = param?.username,
                )
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter(
        var username : String = "",
        var provider : Int = -1

    )
}
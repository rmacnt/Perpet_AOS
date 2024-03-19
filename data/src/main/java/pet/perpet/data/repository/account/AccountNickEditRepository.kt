package pet.perpet.data.repository.account

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.account.UserEntity
import pet.perpet.data.api.entity.response.BaseObjectResponse
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.CashDataSource
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class AccountNickEditRepository : Repository<AccountNickEditRepository.Parameter, UserEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object :
            RemoteDataSource<Parameter, UserEntity, BaseObjectResponse<UserEntity>>() {
            override fun toObject(raw: BaseObjectResponse<UserEntity>?): UserEntity? {
                return raw?.data
            }

            override fun fetchApi(param: Parameter?): ApiCall<BaseObjectResponse<UserEntity>> {
                return Client.account.setNickNameChange(
                    nickname = param?.nickName.nonnull()
                )
            }
        }
    }

    override fun createCashDataSource(): CashDataSource<UserEntity>? {
        return object : CashDataSource<UserEntity>() {
            override val key: String
                get() = AccountInfoRepository::class.java.name
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    data class Parameter(var nickName: String = "")
}
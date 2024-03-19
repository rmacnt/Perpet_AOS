package pet.perpet.data.repository.account

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.account.UserLoginEntity
import pet.perpet.data.api.entity.response.BaseObjectResponse
import pet.perpet.data.api.request.account.AccountRequest
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.CashDataSource
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class AccountLoginRepository : Repository<AccountLoginRepository.Parameter, UserLoginEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object :
            RemoteDataSource<Parameter, UserLoginEntity, BaseObjectResponse<UserLoginEntity>>() {
            override fun toObject(raw: BaseObjectResponse<UserLoginEntity>?): UserLoginEntity? {
                return raw?.data
            }

            override fun fetchApi(param: Parameter?): ApiCall<BaseObjectResponse<UserLoginEntity>> {
                return Client.account.accountSocialLogin(
                    AccountRequest(
                        param?.id.nonnull(),
                        param?.type.nonnull(),
                        param?.name.nonnull(),
                        param?.email.nonnull(),
                        param?.device_unique.nonnull(),
                        param?.device_group.nonnull(),
                        param?.device_num.nonnull(),
                        param?.service_agree.nonnull(),
                        param?.privacy_agree.nonnull(),
                        param?.marketing_agree.nonnull()
                    )
                )
            }
        }
    }

    override fun createCashDataSource(): CashDataSource<UserLoginEntity>? {
        return object : CashDataSource<UserLoginEntity>() {
            override val key: String
                get() = AccountLoginRepository::class.java.name
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    data class Parameter(
        var id: String = "",
        var type: String = "",
        var name: String = "",
        var email: String = "",
        var device_unique: String = "",
        var device_group: String = "",
        var device_num: String = "",
        var service_agree: String = "Y",
        var privacy_agree: String = "Y",
        var marketing_agree: String = "Y"
    )
}
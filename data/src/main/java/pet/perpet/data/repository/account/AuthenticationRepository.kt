package pet.perpet.data.repository.account

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.response.BaseObjectResponse
import pet.perpet.data.api.request.account.AuthenticationRequest
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class AuthenticationRepository : Repository<AuthenticationRepository.Parameter, Any>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object :
            RemoteDataSource<Parameter, Any, BaseObjectResponse<Any>>() {
            override fun toObject(raw: BaseObjectResponse<Any>?): Boolean? {
                return raw?.success
            }

            override fun fetchApi(param: Parameter?): ApiCall<BaseObjectResponse<Any>> {
                return Client.account.authentication(
                    phone = param?.phone.nonnull(),
                    auth_num = param?.auth_num.nonnull()
                )
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    data class Parameter(
        var phone: String? = null,
        var auth_num: String? = null)
}
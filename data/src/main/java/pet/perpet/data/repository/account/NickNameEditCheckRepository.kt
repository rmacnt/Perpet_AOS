package pet.perpet.data.repository.account

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.response.BaseObjectResponse
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class NickNameEditCheckRepository : Repository<NickNameEditCheckRepository.Parameter, Any>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object :
            RemoteDataSource<Parameter, Any, BaseObjectResponse<Any>>() {
            override fun toObject(raw: BaseObjectResponse<Any>?): Any? {
                return raw
            }

            override fun fetchApi(param: Parameter?): ApiCall<BaseObjectResponse<Any>> {
                return Client.account.nicknameCheck(
                    name = param?.nickname.nonnull()
                )
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    data class Parameter(
        var nickname: String = ""
    )
}
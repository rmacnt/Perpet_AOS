package pet.perpet.data.repository.order

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.response.DefaultResponse
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class OrderCancelRepository  : Repository<OrderCancelRepository.Parameter, Any>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {

        return object :
            RemoteDataSource<Parameter, Any, DefaultResponse>() {
            override fun fetchApi(param: Parameter?): ApiCall<DefaultResponse> {
                return Client.main.setOrderCancel(
                    id = param?.id.nonnull()
                )
            }

            override fun toObject(raw: DefaultResponse?): String? {
                return raw?.msg
            }

        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {
        var id: Int = 0
    }
}
package pet.perpet.data.repository.order

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.order.OrderContentEntity
import pet.perpet.data.api.entity.response.BaseObjectResponse
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class OrderNowDetailRepository : Repository<OrderNowDetailRepository.Parameter, OrderContentEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object :
            RemoteDataSource<Parameter, OrderContentEntity, BaseObjectResponse<OrderContentEntity>>() {
            override fun toObject(raw: BaseObjectResponse<OrderContentEntity>?): OrderContentEntity? {
                return raw?.data
            }

            override fun fetchApi(param: Parameter?): ApiCall<BaseObjectResponse<OrderContentEntity>> {
                return Client.main.getNowOrderDetail(
                    id = param?.id.nonnull(),
                )
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {
        var id: Int? = null
    }
}
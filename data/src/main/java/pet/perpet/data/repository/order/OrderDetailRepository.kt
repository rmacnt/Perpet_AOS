package pet.perpet.data.repository.order

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.order.InventoryEntity
import pet.perpet.data.api.entity.response.BaseObjectResponse
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class OrderDetailRepository : Repository<OrderDetailRepository.Parameter, InventoryEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object :
            RemoteDataSource<Parameter, InventoryEntity, BaseObjectResponse<InventoryEntity>>() {
            override fun toObject(raw: BaseObjectResponse<InventoryEntity>?): InventoryEntity? {
                return raw?.data
            }

            override fun fetchApi(param: Parameter?): ApiCall<BaseObjectResponse<InventoryEntity>> {
                return Client.main.getOrderDetail(
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
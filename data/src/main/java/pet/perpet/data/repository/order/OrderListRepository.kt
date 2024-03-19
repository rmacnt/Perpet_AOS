package pet.perpet.data.repository.order

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.order.InventoryEntity
import pet.perpet.data.api.entity.response.BasePageResponse
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class OrderListRepository : Repository<OrderListRepository.Parameter, PageContentsEntity<InventoryEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object : RemoteDataSource<Parameter, PageContentsEntity<InventoryEntity>, BasePageResponse<InventoryEntity>>() {
            override fun fetchApi(param: Parameter?): ApiCall<BasePageResponse<InventoryEntity>> {
                return Client.main.getOrderHistory()
            }

            override fun toObject(raw: BasePageResponse<InventoryEntity>?): PageContentsEntity<InventoryEntity>? {
                return raw?.data
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {
    }

}
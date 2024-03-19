package pet.perpet.data.repository.address

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.order.OrderContentEntity
import pet.perpet.data.api.entity.response.BaseObjectResponse
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class AddressChangeRepository : Repository<AddressChangeRepository.Parameter, OrderContentEntity>() {

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
                return Client.main.setAddressChange(
                    id = param?.id.nonnull(),
                    addressId = param?.addressId.nonnull()
                )
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {
        var id: String = ""
        var addressId: String = ""
    }
}
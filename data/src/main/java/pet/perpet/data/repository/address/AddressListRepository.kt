package pet.perpet.data.repository.address

import pet.perpet.data.api.AddressClient
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.address.AddressEntity
import pet.perpet.data.api.entity.response.BaseAddressPageResponse
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class AddressListRepository : Repository<AddressListRepository.Parameter, AddressEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object :
            RemoteDataSource<Parameter, AddressEntity, BaseAddressPageResponse<AddressEntity>>() {
            override fun toObject(raw: BaseAddressPageResponse<AddressEntity>?): AddressEntity? {
                return raw?.data
            }

            override fun fetchApi(param: Parameter?): ApiCall<BaseAddressPageResponse<AddressEntity>> {
                return AddressClient.address.getAddress(
                    address = param?.address.nonnull()
                )
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    data class Parameter(
        var address: String = ""
    )
}
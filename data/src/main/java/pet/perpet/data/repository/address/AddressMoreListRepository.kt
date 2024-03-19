package pet.perpet.data.repository.address

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.address.ListAddressEntity
import pet.perpet.data.api.entity.response.BasePageResponse
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class AddressMoreListRepository : Repository<AddressMoreListRepository.Parameter, PageContentsEntity<ListAddressEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object : RemoteDataSource<Parameter, PageContentsEntity<ListAddressEntity>, BasePageResponse<ListAddressEntity>>() {
            override fun fetchApi(param: Parameter?): ApiCall<BasePageResponse<ListAddressEntity>> {
                return Client.main.getAddressList()
            }

            override fun toObject(raw: BasePageResponse<ListAddressEntity>?): PageContentsEntity<ListAddressEntity>? {
                return raw?.data
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {}

}
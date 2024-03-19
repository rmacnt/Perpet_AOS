package pet.perpet.data.repository.address

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.address.ListAddressEntity
import pet.perpet.data.api.entity.response.BaseObjectResponse
import pet.perpet.data.api.request.address.AddressInsertRequest
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class AddressInsertRepository : Repository<AddressInsertRepository.Parameter, ListAddressEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object :
            RemoteDataSource<Parameter, ListAddressEntity, BaseObjectResponse<ListAddressEntity>>() {
            override fun toObject(raw: BaseObjectResponse<ListAddressEntity>?): ListAddressEntity? {
                return raw?.data
            }

            override fun fetchApi(param: Parameter?): ApiCall<BaseObjectResponse<ListAddressEntity>> {
                return Client.main.setAddressInsert(
                    AddressInsertRequest(
                        recipient = param?.recipient,
                        phone = param?.phone,
                        zipcode = param?.zipcode,
                        address = param?.address,
                        address_detail = param?.address_detail,
                        memo =  param?.memo.nonnull()
                    )
                )
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {
        var recipient: String = ""
        var phone: String = ""
        var zipcode: String = ""
        var address: String = ""
        var address_detail: String = ""
        var memo: String = ""
    }
}
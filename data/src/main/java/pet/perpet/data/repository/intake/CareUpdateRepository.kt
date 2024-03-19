package pet.perpet.data.repository.intake

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.response.DefaultResponse
import pet.perpet.data.api.request.intake.CareUpdateRequest
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class CareUpdateRepository : Repository<CareUpdateRepository.Parameter, Any>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {

        return object :
            RemoteDataSource<Parameter, Any, DefaultResponse>() {
            override fun fetchApi(param: Parameter?): ApiCall<DefaultResponse> {
                return Client.intake.setCareUpdate(
                    careUpdateRequest = CareUpdateRequest(
                        id = param?.id.nonnull(),
                        pet_id = param?.petId.nonnull(),
                        order_id = param?.orderId.nonnull(),
                        result = param?.result.nonnull(),
                        date = param?.date.nonnull()
                    )
                )
            }

            override fun toObject(raw: DefaultResponse?): Any? {
                return raw
            }

        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {
        var id: Int? = 0
        var petId: Int? = 0
        var orderId: Int? = 0
        var result: String? = ""
        var date: String? = ""
    }
}
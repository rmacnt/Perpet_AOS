package pet.perpet.data.repository.card

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.response.DefaultResponse
import pet.perpet.data.api.request.main.CardInsertRequest
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class CardInsertRepository  : Repository<CardInsertRepository.Parameter, Any>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {

        return object :
            RemoteDataSource<Parameter, Any, DefaultResponse>() {
            override fun fetchApi(param: Parameter?): ApiCall<DefaultResponse> {
                return Client.main.setCardInsert(
                        CardInsertRequest(
                            type =  "card",
                            card_number = param?.cardNumber.nonnull(),
                            expiry = param?.cardYear.nonnull(),
                            pwd2digit = param?.cardPass.nonnull(),
                            birth = param?.birth.nonnull()
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
        var cardNumber: String = ""
        var cardYear: String = ""
        var cardPass: String = ""
        var birth: String = ""
    }
}
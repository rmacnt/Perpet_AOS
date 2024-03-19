package pet.perpet.data.repository.card

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.card.CardChangeEntity
import pet.perpet.data.api.entity.response.BaseObjectResponse
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class CardChangeRepository : Repository<CardChangeRepository.Parameter, CardChangeEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object :
            RemoteDataSource<Parameter, CardChangeEntity, BaseObjectResponse<CardChangeEntity>>() {
            override fun toObject(raw: BaseObjectResponse<CardChangeEntity>?): CardChangeEntity? {
                return raw?.data
            }

            override fun fetchApi(param: Parameter?): ApiCall<BaseObjectResponse<CardChangeEntity>> {
                return Client.main.setCardChange(
                    id = param?.id.nonnull(),
                    cardId = param?.cardId.nonnull()
                )
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {
        var id: String = ""
        var cardId: String = ""
    }
}
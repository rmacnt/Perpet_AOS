package pet.perpet.data.repository.card

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.card.CardListEntity
import pet.perpet.data.api.entity.response.BasePageResponse
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class CardListRepository : Repository<CardListRepository.Parameter, PageContentsEntity<CardListEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object : RemoteDataSource<Parameter, PageContentsEntity<CardListEntity>, BasePageResponse<CardListEntity>>() {
            override fun fetchApi(param: Parameter?): ApiCall<BasePageResponse<CardListEntity>> {
                return Client.main.getCardList()
            }

            override fun toObject(raw: BasePageResponse<CardListEntity>?): PageContentsEntity<CardListEntity>? {
                return raw?.data
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {}

}
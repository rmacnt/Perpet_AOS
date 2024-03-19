package pet.perpet.data.repository.main

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.main.MainCardEntity
import pet.perpet.data.api.entity.response.BasePageResponse
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class MainCardRepository : Repository<MainCardRepository.Parameter, PageContentsEntity<MainCardEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object : RemoteDataSource<Parameter, PageContentsEntity<MainCardEntity>, BasePageResponse<MainCardEntity>>() {
            override fun fetchApi(param: Parameter?): ApiCall<BasePageResponse<MainCardEntity>> {
                return Client.main.getMainCard(
                    userId = param?.userId.nonnull(),
                    petId = param?.petId.nonnull()
                )
            }

            override fun toObject(raw: BasePageResponse<MainCardEntity>?): PageContentsEntity<MainCardEntity>? {
                return raw?.data
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {
        var userId: String? = ""
        var petId: String? = ""
    }

}
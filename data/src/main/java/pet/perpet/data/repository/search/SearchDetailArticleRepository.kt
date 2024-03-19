package pet.perpet.data.repository.search

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.response.BaseObjectResponse
import pet.perpet.data.api.entity.search.SearchArticleEntity
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class SearchDetailArticleRepository : Repository<SearchDetailArticleRepository.Parameter, SearchArticleEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object :
            RemoteDataSource<Parameter, SearchArticleEntity, BaseObjectResponse<SearchArticleEntity>>() {
            override fun toObject(raw: BaseObjectResponse<SearchArticleEntity>?): SearchArticleEntity? {
                return raw?.data
            }

            override fun fetchApi(param: Parameter?): ApiCall<BaseObjectResponse<SearchArticleEntity>> {
                return Client.main.getSearchCard(
                    id = param?.id.nonnull(),
                    userId = param?.userId.nonnull()
                )
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {
        var id: Int = 0
        var userId: String = ""
    }
}
package pet.perpet.data.repository.search

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.response.BasePageResponse
import pet.perpet.data.api.entity.search.SearchArticleEntity
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class SearchArticleListRepository : Repository<SearchArticleListRepository.Parameter, PageContentsEntity<SearchArticleEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object : RemoteDataSource<Parameter, PageContentsEntity<SearchArticleEntity>, BasePageResponse<SearchArticleEntity>>() {
            override fun fetchApi(param: Parameter?): ApiCall<BasePageResponse<SearchArticleEntity>> {
                return Client.main.getSearchArticle(
                    page = param?.page.nonnull(),
                    type = param?.type.nonnull(),
                    search = param?.search.nonnull()

                )
            }

            override fun toObject(raw: BasePageResponse<SearchArticleEntity>?): PageContentsEntity<SearchArticleEntity>? {
                return raw?.data
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {
        var page: Int = 0
        var type: String = "article"
        var search: String = ""
    }

}
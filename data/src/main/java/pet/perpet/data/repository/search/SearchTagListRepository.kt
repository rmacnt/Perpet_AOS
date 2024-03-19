package pet.perpet.data.repository.search

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.response.BasePageResponse
import pet.perpet.data.api.entity.search.SearchTagEntity
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class SearchTagListRepository : Repository<SearchTagListRepository.Parameter, PageContentsEntity<SearchTagEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object : RemoteDataSource<Parameter, PageContentsEntity<SearchTagEntity>, BasePageResponse<SearchTagEntity>>() {
            override fun fetchApi(param: Parameter?): ApiCall<BasePageResponse<SearchTagEntity>> {
                return Client.main.getSearchTag(
                    page = param?.page.nonnull(),
                    search = param?.search.nonnull()
                )
            }

            override fun toObject(raw: BasePageResponse<SearchTagEntity>?): PageContentsEntity<SearchTagEntity>? {
                return raw?.data
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {
        var page: Int = 0
        var type: String = "tag"
        var search: String = ""
    }

}
package pet.perpet.data.repository.search

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.response.BasePageResponse
import pet.perpet.data.api.entity.search.SearchProductEntity
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

    class SearchSuppleListRepository : Repository<SearchSuppleListRepository.Parameter, PageContentsEntity<SearchProductEntity>>() {

        //======================================================================
        // Override Methods
        //======================================================================

        override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
            return object : RemoteDataSource<Parameter, PageContentsEntity<SearchProductEntity>, BasePageResponse<SearchProductEntity>>() {
                override fun fetchApi(param: Parameter?): ApiCall<BasePageResponse<SearchProductEntity>> {
                    return Client.main.getSearchProduct(
                        page = param?.page.nonnull(),
                        search = param?.search.nonnull()
                    )
                }

                override fun toObject(raw: BasePageResponse<SearchProductEntity>?): PageContentsEntity<SearchProductEntity>? {
                    return raw?.data
                }
            }
        }

        //======================================================================
        // Parameter
        //======================================================================

        open class Parameter {
            var page: Int = 0
            var search: String = ""
        }

    }
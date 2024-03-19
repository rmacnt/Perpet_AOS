package pet.perpet.data.repository.search

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.response.BaseObjectResponse
import pet.perpet.data.api.entity.search.SearchProductEntity
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class SearchDetailProductRepository : Repository<SearchDetailProductRepository.Parameter, SearchProductEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object :
            RemoteDataSource<Parameter, SearchProductEntity, BaseObjectResponse<SearchProductEntity>>() {
            override fun toObject(raw: BaseObjectResponse<SearchProductEntity>?): SearchProductEntity? {
                return raw?.data
            }

            override fun fetchApi(param: Parameter?): ApiCall<BaseObjectResponse<SearchProductEntity>> {
                return Client.main.getSearchProduct(
                    id = param?.id.nonnull()
                )
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {
        var id: Int = 0
    }
}
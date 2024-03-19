package pet.perpet.data.repository.search

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.response.BaseListResponse
import pet.perpet.data.api.entity.search.SearchSimpleEntity
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class SearchSimpleRepository : Repository<SearchSimpleRepository.Parameter, ArrayList<SearchSimpleEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object : RemoteDataSource<Parameter, ArrayList<SearchSimpleEntity>, BaseListResponse<SearchSimpleEntity>>() {
            override fun fetchApi(param: Parameter?): ApiCall<BaseListResponse<SearchSimpleEntity>> {
                return Client.main.getIntegratedSearch(
                    search = param?.search.nonnull()
                )
            }

            override fun toObject(raw: BaseListResponse<SearchSimpleEntity>?): ArrayList<SearchSimpleEntity>? {
                return raw?.data
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {
        var search: String = ""
    }

}
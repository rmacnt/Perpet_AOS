package pet.perpet.data.repository.profile

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.profile.BreedEntity
import pet.perpet.data.api.entity.response.BasePageResponse
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class BreedRepository : Repository<BreedRepository.Parameter, PageContentsEntity<BreedEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object : RemoteDataSource<Parameter, PageContentsEntity<BreedEntity>, BasePageResponse<BreedEntity>>() {
            override fun fetchApi(param: Parameter?): ApiCall<BasePageResponse<BreedEntity>> {
                return Client.account.getBreedsList(
                    param?.search.orEmpty(),
                    param?.type.orEmpty()
                )
            }

            override fun toObject(raw: BasePageResponse<BreedEntity>?): PageContentsEntity<BreedEntity>? {
                return raw?.data
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {
        var search: String? = null
        var type: String? = null

    }

}
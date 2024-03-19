package pet.perpet.data.repository.pet

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.pet.PetEntity
import pet.perpet.data.api.entity.response.BasePageResponse
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class PetListRepository : Repository<PetListRepository.Parameter, PageContentsEntity<PetEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object : RemoteDataSource<Parameter, PageContentsEntity<PetEntity>, BasePageResponse<PetEntity>>() {
            override fun fetchApi(param: Parameter?): ApiCall<BasePageResponse<PetEntity>> {
                return Client.account.getPetList()
            }

            override fun toObject(raw: BasePageResponse<PetEntity>?): PageContentsEntity<PetEntity>? {
                return raw?.data
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {}

}
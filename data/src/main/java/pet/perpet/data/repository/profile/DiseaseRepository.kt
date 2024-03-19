package pet.perpet.data.repository.profile

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.profile.DiseaseEntity
import pet.perpet.data.api.entity.response.BasePageResponse
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class DiseaseRepository : Repository<DiseaseRepository.Parameter, PageContentsEntity<DiseaseEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object : RemoteDataSource<Parameter, PageContentsEntity<DiseaseEntity>, BasePageResponse<DiseaseEntity>>() {
            override fun fetchApi(param: Parameter?): ApiCall<BasePageResponse<DiseaseEntity>> {
                return Client.account.getDiseaseList(
                    is_main = param?.isMain.nonnull()
                )
            }

            override fun toObject(raw: BasePageResponse<DiseaseEntity>?): PageContentsEntity<DiseaseEntity>? {
                return raw?.data
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {
        var isMain : Boolean = false
    }

}
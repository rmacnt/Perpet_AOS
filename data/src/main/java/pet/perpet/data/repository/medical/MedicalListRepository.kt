package pet.perpet.data.repository.medical

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.medical.MedicalEntity
import pet.perpet.data.api.entity.response.BasePageResponse
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class MedicalListRepository : Repository<MedicalListRepository.Parameter, PageContentsEntity<MedicalEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object : RemoteDataSource<Parameter, PageContentsEntity<MedicalEntity>, BasePageResponse<MedicalEntity>>() {
            override fun fetchApi(param: Parameter?): ApiCall<BasePageResponse<MedicalEntity>> {
                return Client.medical.getMedicalList(
                    petId = param?.petId.nonnull()
                )
            }

            override fun toObject(raw: BasePageResponse<MedicalEntity>?): PageContentsEntity<MedicalEntity>? {
                return raw?.data
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {
        var petId: Int? = null
    }

}
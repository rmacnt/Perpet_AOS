package pet.perpet.data.repository.medical

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.medical.MedicalEntity
import pet.perpet.data.api.entity.response.BaseObjectResponse
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class MedicalResultRepository : Repository<MedicalResultRepository.Parameter, MedicalEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object :
            RemoteDataSource<Parameter, MedicalEntity, BaseObjectResponse<MedicalEntity>>() {
            override fun toObject(raw: BaseObjectResponse<MedicalEntity>?): MedicalEntity? {
                return raw?.data
            }

            override fun fetchApi(param: Parameter?): ApiCall<BaseObjectResponse<MedicalEntity>> {
                return Client.medical.getMedicalInfo(
                    id = param?.id.toString().nonnull()
                )
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    data class Parameter(
        var id: Int = 0,
    )
}
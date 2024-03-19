package pet.perpet.data.repository.pet

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.pet.PetEntity
import pet.perpet.data.api.entity.response.BaseObjectResponse
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class PetInfoRepository : Repository<PetInfoRepository.Parameter, PetEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object :
            RemoteDataSource<Parameter, PetEntity, BaseObjectResponse<PetEntity>>() {
            override fun toObject(raw: BaseObjectResponse<PetEntity>?): PetEntity? {
                return raw?.data
            }

            override fun fetchApi(param: Parameter?): ApiCall<BaseObjectResponse<PetEntity>> {
                return Client.account.petInfo(
                    id = param?.pet_id.nonnull().toString(),
                )
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    data class Parameter(
        var pet_id: Int = 0
    )
}
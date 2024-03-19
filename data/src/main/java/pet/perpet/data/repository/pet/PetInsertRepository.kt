package pet.perpet.data.repository.pet

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.pet.PetEntity
import pet.perpet.data.api.entity.response.BaseObjectResponse
import pet.perpet.data.api.request.pet.PetInsertRequest
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.CashDataSource
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class PetInsertRepository : Repository<PetInsertRepository.Parameter, PetEntity>() {

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
                return Client.account.petInsert(
                    petInsertRequest = param?.petInsertRequest,
                )
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {
        var petInsertRequest: PetInsertRequest? = null
    }
}
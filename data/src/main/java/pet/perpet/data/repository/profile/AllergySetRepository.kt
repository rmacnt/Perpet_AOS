package pet.perpet.data.repository.profile

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.profile.AllergyEntity
import pet.perpet.data.api.entity.response.BaseObjectResponse
import pet.perpet.data.api.request.pet.AllergyInsertRequest
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class AllergySetRepository : Repository<AllergySetRepository.Parameter, AllergyEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object :
            RemoteDataSource<Parameter, AllergyEntity, BaseObjectResponse<AllergyEntity>>() {
            override fun toObject(raw: BaseObjectResponse<AllergyEntity>?): AllergyEntity? {
                return raw?.data
            }

            override fun fetchApi(param: Parameter?): ApiCall<BaseObjectResponse<AllergyEntity>> {
                return Client.account.setAllergy(
                    allergyInsertRequest = AllergyInsertRequest(
                        name = param?.name.nonnull(),
                        user_id = param?.userId.nonnull()
                    )
                )
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {
        var name: String  = ""
        var userId: Int  = 0
    }
}
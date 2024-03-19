package pet.perpet.data.repository.profile

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.profile.BreedEntity
import pet.perpet.data.api.entity.response.BaseObjectResponse
import pet.perpet.data.api.request.pet.BreedInsertRequest
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class BreedSetRepository : Repository<BreedSetRepository.Parameter, BreedEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object :
            RemoteDataSource<Parameter, BreedEntity, BaseObjectResponse<BreedEntity>>() {
            override fun toObject(raw: BaseObjectResponse<BreedEntity>?): BreedEntity? {
                return raw?.data
            }

            override fun fetchApi(param: Parameter?): ApiCall<BaseObjectResponse<BreedEntity>> {
                return Client.account.setBreed(
                    BreedInsertRequest(
                        name = param?.name.nonnull(),
                        type = param?.type.nonnull(),
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
        var name: String = ""
        var type: String = ""
        var userId: Int = 0
    }
}
package pet.perpet.data.repository.medical

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.medical.TypeformEntity
import pet.perpet.data.api.entity.response.BaseObjectResponse
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class TypeformInfoRepository : Repository<TypeformInfoRepository.Parameter, TypeformEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object :
            RemoteDataSource<Parameter, TypeformEntity, BaseObjectResponse<TypeformEntity>>() {
            override fun toObject(raw: BaseObjectResponse<TypeformEntity>?): TypeformEntity? {
                return raw?.data
            }

            override fun fetchApi(param: Parameter?): ApiCall<BaseObjectResponse<TypeformEntity>> {
                return Client.medical.getTypeformInfo(
                    user_id = param?.user_id.nonnull(),
                    pet_id = param?.pet_id.nonnull(),
                    type = param?.type.nonnull()
                )
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    data class Parameter(
        var user_id: Int = 0,
        var pet_id: Int = 0,
        var type: String = "",
        )
}
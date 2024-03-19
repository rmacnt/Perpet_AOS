package pet.perpet.data.repository.profile

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.profile.AllergyEntity
import pet.perpet.data.api.entity.response.BasePageResponse
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class AllergyRepository : Repository<AllergyRepository.Parameter, PageContentsEntity<AllergyEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object : RemoteDataSource<Parameter, PageContentsEntity<AllergyEntity>, BasePageResponse<AllergyEntity>>() {
            override fun fetchApi(param: Parameter?): ApiCall<BasePageResponse<AllergyEntity>> {
                return Client.account.getAllergyList(
                    name = param?.name.orEmpty(),
                )
            }

            override fun toObject(raw: BasePageResponse<AllergyEntity>?): PageContentsEntity<AllergyEntity>? {
                return raw?.data
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {
        var name: String? = ""
    }

}
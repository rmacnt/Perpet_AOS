package pet.perpet.data.repository.intake

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.intake.IntakeDataEntity
import pet.perpet.data.api.entity.response.BaseListResponse
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class CareListRepository : Repository<CareListRepository.Parameter, ArrayList<IntakeDataEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object : RemoteDataSource<Parameter, ArrayList<IntakeDataEntity>, BaseListResponse<IntakeDataEntity>>() {
            override fun fetchApi(param: Parameter?): ApiCall<BaseListResponse<IntakeDataEntity>> {
                return Client.intake.getCareList(
                    startDate = param?.startDate.nonnull(),
                    endDate = param?.endDate.nonnull()
                )
            }

            override fun toObject(raw: BaseListResponse<IntakeDataEntity>?): ArrayList<IntakeDataEntity>? {
                return raw?.data
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {
        var startDate: String = ""
        var endDate: String = ""
    }

}
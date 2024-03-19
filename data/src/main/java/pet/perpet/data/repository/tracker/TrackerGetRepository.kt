package pet.perpet.data.repository.tracker

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.response.BaseObjectResponse
import pet.perpet.data.api.entity.tracker.TrackerEntity
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class TrackerGetRepository : Repository<TrackerGetRepository.Parameter, TrackerEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object :
            RemoteDataSource<Parameter, TrackerEntity, BaseObjectResponse<TrackerEntity>>() {
            override fun toObject(raw: BaseObjectResponse<TrackerEntity>?): TrackerEntity? {
                return raw?.data
            }

            override fun fetchApi(param: Parameter?): ApiCall<BaseObjectResponse<TrackerEntity>> {
                return Client.main.getTracker(
                    carrier = param?.carrier.nonnull(),
                    trackerid = param?.trackerid.nonnull()
                )
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {
        var carrier: String = ""
        var trackerid: String = ""
    }
}
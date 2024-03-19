package pet.perpet.data.repository.main

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.main.FeedEntity
import pet.perpet.data.api.entity.response.BaseObjectResponse
import pet.perpet.data.api.request.main.FeedbackRequest
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class FeedSetRepository : Repository<FeedSetRepository.Parameter, FeedEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object :
            RemoteDataSource<Parameter, FeedEntity, BaseObjectResponse<FeedEntity>>() {
            override fun toObject(raw: BaseObjectResponse<FeedEntity>?): FeedEntity? {
                return raw?.data
            }

            override fun fetchApi(param: Parameter?): ApiCall<BaseObjectResponse<FeedEntity>> {
                return Client.main.setFeed(
                    FeedbackRequest(
                        contents = param?.contents,
                        target_id = param?.targetId
                    )
                )
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {
        var contents : String = ""
        var targetId : Long = 0
    }
}
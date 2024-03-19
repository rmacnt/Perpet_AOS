package pet.perpet.data.repository.notification

import android.util.Log
import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.notification.PushListEntity
import pet.perpet.data.api.entity.response.BasePageResponse
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class PushListRepository : Repository<PushListRepository.Parameter, PageContentsEntity<PushListEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object : RemoteDataSource<Parameter, PageContentsEntity<PushListEntity>, BasePageResponse<PushListEntity>>() {
            override fun fetchApi(param: Parameter?): ApiCall<BasePageResponse<PushListEntity>> {
                return Client.notification.getPushList(
                    page = param?.page.nonnull(),
                    limit = param?.limit.nonnull()
                )
            }

            override fun toObject(raw: BasePageResponse<PushListEntity>?): PageContentsEntity<PushListEntity>? {
                return raw?.data
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {
        var page: Int? = null
        var limit: Int? = null
    }

}
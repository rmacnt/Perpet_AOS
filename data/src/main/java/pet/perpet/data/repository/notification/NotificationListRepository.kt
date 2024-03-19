package pet.perpet.data.repository.notification

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.notification.NoticeEntity
import pet.perpet.data.api.entity.response.BasePageResponse
import pet.perpet.data.nonnull
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class NotificationListRepository  : Repository<NotificationListRepository.Parameter, PageContentsEntity<NoticeEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object : RemoteDataSource<Parameter, PageContentsEntity<NoticeEntity>, BasePageResponse<NoticeEntity>>() {
            override fun fetchApi(param: Parameter?): ApiCall<BasePageResponse<NoticeEntity>> {
                return Client.notification.getBBSList(
                    param?.page.nonnull(),
                    param?.fieldType.nonnull()
                )
            }

            override fun toObject(raw: BasePageResponse<NoticeEntity>?): PageContentsEntity<NoticeEntity>? {
                return raw?.data
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {
        var page: Int? = null
        var fieldType: Int? = null
    }

}
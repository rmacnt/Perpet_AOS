package pet.perpet.domain.usecase.notification

import com.google.gson.reflect.TypeToken
import pet.perpet.data.GsonLoader.gson
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.notification.NoticeEntity
import pet.perpet.data.repository.notification.NotificationListRepository
import pet.perpet.domain.model.notification.Notice
import pet.perpet.domain.model.page.PageContents
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class NoticeUseCase :
    UseCase<NotificationListRepository, NoticeUseCase.Parameter, PageContents<Notice>, PageContentsEntity<NoticeEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: PageContentsEntity<NoticeEntity>?): PageContents<Notice>? {
        return raw?.let {
            gson.fromJson(
                it.toJson(),
                object : TypeToken<PageContents<Notice>>() {}.type
            )
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    class Parameter : NotificationListRepository.Parameter()
}
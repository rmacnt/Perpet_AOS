package pet.perpet.domain.usecase.notification

import com.google.gson.reflect.TypeToken
import pet.perpet.data.GsonLoader
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.notification.PushListEntity
import pet.perpet.data.repository.notification.PushListRepository
import pet.perpet.domain.model.notification.PushList
import pet.perpet.domain.model.page.PageContents
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class PushListUseCase  :
    UseCase<PushListRepository, PushListRepository.Parameter, PageContents<PushList>, PageContentsEntity<PushListEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: PageContentsEntity<PushListEntity>?): PageContents<PushList>? {
        return raw?.let {
            GsonLoader.gson.fromJson(
                it.toJson(),
                object : TypeToken<PageContents<PushList>>() {}.type
            )
        }
    }

}
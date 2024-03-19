package pet.perpet.domain.usecase.main

import com.google.gson.reflect.TypeToken
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.main.MainCardEntity
import pet.perpet.data.repository.main.MainCardRepository
import pet.perpet.domain.GsonLoader
import pet.perpet.domain.model.main.MainCard
import pet.perpet.domain.model.page.PageContents
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class MainCardUseCase :
    UseCase<MainCardRepository, MainCardRepository.Parameter, PageContents<MainCard>, PageContentsEntity<MainCardEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: PageContentsEntity<MainCardEntity>?): PageContents<MainCard>? {
        return GsonLoader.gson.fromJson(
            raw.toJson(),
            object : TypeToken<PageContents<MainCard>>() {}.type
        )
    }

}
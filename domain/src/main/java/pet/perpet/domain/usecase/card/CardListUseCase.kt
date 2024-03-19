package pet.perpet.domain.usecase.card

import com.google.gson.reflect.TypeToken
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.card.CardListEntity
import pet.perpet.data.repository.card.CardListRepository
import pet.perpet.domain.GsonLoader
import pet.perpet.domain.model.card.CardList
import pet.perpet.domain.model.page.PageContents
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class CardListUseCase :
    UseCase<CardListRepository, CardListRepository.Parameter, PageContents<CardList>, PageContentsEntity<CardListEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: PageContentsEntity<CardListEntity>?): PageContents<CardList>? {
        return GsonLoader.gson.fromJson(
            raw.toJson(),
            object : TypeToken<PageContents<CardList>>() {}.type
        )
    }

}
package pet.perpet.domain.usecase.search

import com.google.gson.reflect.TypeToken
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.search.SearchTagEntity
import pet.perpet.data.repository.search.SearchTagListRepository
import pet.perpet.domain.GsonLoader
import pet.perpet.domain.model.page.PageContents
import pet.perpet.domain.model.search.SearchTag
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class SearchTagListUseCase :
    UseCase<SearchTagListRepository, SearchTagListRepository.Parameter, PageContents<SearchTag>, PageContentsEntity<SearchTagEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: PageContentsEntity<SearchTagEntity>?): PageContents<SearchTag>? {
        return GsonLoader.gson.fromJson(
            raw.toJson(),
            object : TypeToken<PageContents<SearchTag>>() {}.type
        )
    }

}
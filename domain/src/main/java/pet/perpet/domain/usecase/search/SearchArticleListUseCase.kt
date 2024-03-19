package pet.perpet.domain.usecase.search

import com.google.gson.reflect.TypeToken
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.search.SearchArticleEntity
import pet.perpet.domain.model.search.SearchArticle
import pet.perpet.data.repository.search.SearchArticleListRepository
import pet.perpet.domain.GsonLoader
import pet.perpet.domain.model.page.PageContents
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class SearchArticleListUseCase :
    UseCase<SearchArticleListRepository, SearchArticleListRepository.Parameter, PageContents<SearchArticle>, PageContentsEntity<SearchArticleEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: PageContentsEntity<SearchArticleEntity>?): PageContents<SearchArticle>? {
        return GsonLoader.gson.fromJson(
            raw.toJson(),
            object : TypeToken<PageContents<SearchArticle>>() {}.type
        )
    }

}
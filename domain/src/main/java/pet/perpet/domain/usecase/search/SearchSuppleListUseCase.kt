package pet.perpet.domain.usecase.search

import com.google.gson.reflect.TypeToken
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.search.SearchProductEntity
import pet.perpet.data.repository.search.SearchSuppleListRepository
import pet.perpet.domain.GsonLoader
import pet.perpet.domain.model.page.PageContents
import pet.perpet.domain.model.search.SearchProduct
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class SearchSuppleListUseCase :
    UseCase<SearchSuppleListRepository, SearchSuppleListRepository.Parameter, PageContents<SearchProduct>, PageContentsEntity<SearchProductEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: PageContentsEntity<SearchProductEntity>?): PageContents<SearchProduct>? {
        return GsonLoader.gson.fromJson(
            raw.toJson(),
            object : TypeToken<PageContents<SearchProduct>>() {}.type
        )
    }

}
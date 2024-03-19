package pet.perpet.domain.usecase.search

import pet.perpet.data.api.entity.search.SearchArticleEntity
import pet.perpet.data.repository.search.SearchDetailArticleRepository
import pet.perpet.domain.gson
import pet.perpet.domain.model.search.SearchArticle
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class SearchDetailArticleUseCase :
    UseCase<SearchDetailArticleRepository, SearchDetailArticleRepository.Parameter, SearchArticle, SearchArticleEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: SearchArticleEntity?): SearchArticle? {
        return raw?.toJson()?.let {
            gson.fromJson(it, SearchArticle::class.java)
        }
    }
}
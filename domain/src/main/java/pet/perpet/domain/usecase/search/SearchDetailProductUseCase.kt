package pet.perpet.domain.usecase.search

import pet.perpet.data.api.entity.search.SearchProductEntity
import pet.perpet.data.repository.address.AddressChangeRepository
import pet.perpet.data.repository.search.SearchDetailProductRepository
import pet.perpet.domain.gson
import pet.perpet.domain.model.search.SearchProduct
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class SearchDetailProductUseCase :
    UseCase<SearchDetailProductRepository, SearchDetailProductRepository.Parameter, SearchProduct, SearchProductEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: SearchProductEntity?): SearchProduct? {
        return raw?.toJson()?.let {
            gson.fromJson(it, SearchProduct::class.java)
        }
    }
}
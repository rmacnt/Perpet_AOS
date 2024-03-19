package pet.perpet.domain.usecase.product

import com.google.gson.reflect.TypeToken
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.product.ProductEntity
import pet.perpet.data.repository.product.ProductListRepository
import pet.perpet.domain.GsonLoader
import pet.perpet.domain.model.page.PageContents
import pet.perpet.domain.model.product.Product
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class ProductListUseCase :
    UseCase<ProductListRepository, ProductListRepository.Parameter, PageContents<Product>, PageContentsEntity<ProductEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: PageContentsEntity<ProductEntity>?): PageContents<Product>? {
        return GsonLoader.gson.fromJson(
            raw.toJson(),
            object : TypeToken<PageContents<Product>>() {}.type
        )
    }

}
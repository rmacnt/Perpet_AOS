package pet.perpet.data.repository.product

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.product.ProductEntity
import pet.perpet.data.api.entity.response.BasePageResponse
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class ProductListRepository : Repository<ProductListRepository.Parameter, PageContentsEntity<ProductEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object : RemoteDataSource<Parameter, PageContentsEntity<ProductEntity>, BasePageResponse<ProductEntity>>() {
            override fun fetchApi(param: Parameter?): ApiCall<BasePageResponse<ProductEntity>> {
                return Client.main.getProductList()
            }

            override fun toObject(raw: BasePageResponse<ProductEntity>?): PageContentsEntity<ProductEntity>? {
                return raw?.data
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {}

}
package pet.perpet.data.repository.bookmark

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.bookmark.BookmarkEntity
import pet.perpet.data.api.entity.response.BasePageResponse
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class BookmarkListRepository : Repository<BookmarkListRepository.Parameter, PageContentsEntity<BookmarkEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object : RemoteDataSource<Parameter, PageContentsEntity<BookmarkEntity>, BasePageResponse<BookmarkEntity>>() {
            override fun fetchApi(param: Parameter?): ApiCall<BasePageResponse<BookmarkEntity>> {
                return Client.main.getBookmark(
                    petId = param?.petId
                )
            }

            override fun toObject(raw: BasePageResponse<BookmarkEntity>?): PageContentsEntity<BookmarkEntity>? {
                return raw?.data
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {
        var petId :String = ""
    }

}
package pet.perpet.data.repository.bookmark

import pet.perpet.data.api.Client
import pet.perpet.data.api.call.ApiCall
import pet.perpet.data.api.entity.bookmark.BookmarkSetEntity
import pet.perpet.data.api.entity.response.BaseObjectResponse
import pet.perpet.data.api.request.bookmark.BookmarkSetRequest
import pet.perpet.data.repository.base.Repository
import pet.perpet.data.repository.base.datasource.RemoteDataSource

class BookmarkSetRepository : Repository<BookmarkSetRepository.Parameter, BookmarkSetEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun createRemoteDataSource(): RemoteDataSource<Parameter, *, *> {
        return object :
            RemoteDataSource<Parameter, BookmarkSetEntity, BaseObjectResponse<BookmarkSetEntity>>() {
            override fun toObject(raw: BaseObjectResponse<BookmarkSetEntity>?): BookmarkSetEntity? {
                return raw?.data
            }

            override fun fetchApi(param: Parameter?): ApiCall<BaseObjectResponse<BookmarkSetEntity>> {
                return Client.main.setBookmark(
                    BookmarkSetRequest(
                        pet_id = param?.petId,
                        target_id = param?.targetId
                    )
                )
            }
        }
    }

    //======================================================================
    // Parameter
    //======================================================================

    open class Parameter {
        var petId : Int = 0
        var targetId : Int = 0
    }
}
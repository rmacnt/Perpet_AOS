package pet.perpet.domain.usecase.bookmark

import com.google.gson.reflect.TypeToken
import pet.perpet.data.api.entity.PageContentsEntity
import pet.perpet.data.api.entity.bookmark.BookmarkEntity
import pet.perpet.data.repository.bookmark.BookmarkListRepository
import pet.perpet.domain.GsonLoader
import pet.perpet.domain.model.bookmark.Bookmark
import pet.perpet.domain.model.page.PageContents
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class BookmarkListUseCase :
    UseCase<BookmarkListRepository, BookmarkListRepository.Parameter, PageContents<Bookmark>, PageContentsEntity<BookmarkEntity>>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: PageContentsEntity<BookmarkEntity>?): PageContents<Bookmark>? {
        return GsonLoader.gson.fromJson(
            raw.toJson(),
            object : TypeToken<PageContents<Bookmark>>() {}.type
        )
    }

}
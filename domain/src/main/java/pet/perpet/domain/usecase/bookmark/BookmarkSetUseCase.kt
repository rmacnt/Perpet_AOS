package pet.perpet.domain.usecase.bookmark

import pet.perpet.data.api.entity.bookmark.BookmarkSetEntity
import pet.perpet.data.repository.bookmark.BookmarkSetRepository
import pet.perpet.domain.gson
import pet.perpet.domain.model.bookmark.BookmarkSet
import pet.perpet.domain.toJson
import pet.perpet.domain.usecase.UseCase

class BookmarkSetUseCase  :
    UseCase<BookmarkSetRepository, BookmarkSetRepository.Parameter, BookmarkSet, BookmarkSetEntity>() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun toObject(raw: BookmarkSetEntity?): BookmarkSet? {
        return raw?.toJson()?.let {
            gson.fromJson(it, BookmarkSet::class.java)
        }
    }
}
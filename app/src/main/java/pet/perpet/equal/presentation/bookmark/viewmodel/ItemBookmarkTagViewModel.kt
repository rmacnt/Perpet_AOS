package pet.perpet.equal.presentation.bookmark.viewmodel

import android.view.View
import pet.perpet.data.nonnull
import pet.perpet.domain.model.bookmark.BookmarkTag

class ItemBookmarkTagViewModel  (var model: BookmarkTag? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val name: String?
        get() = model?.name.orEmpty()

    private var notify: ((value: String) -> Unit)? = null

    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: ((value: String) -> Unit)?) {
        notify = value
    }


    fun onDetailClick(view: View) {
        notify?.let {call ->
            call(model?.name.nonnull())
        }
    }
}
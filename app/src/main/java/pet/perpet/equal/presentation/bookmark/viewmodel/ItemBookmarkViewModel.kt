package pet.perpet.equal.presentation.bookmark.viewmodel

import android.view.View
import pet.perpet.domain.model.bookmark.Bookmark

class ItemBookmarkViewModel(var model: Bookmark? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val image: String?
        get() = model?.image

    val top: String?
        get() = model?.top

    val summary: String?
        get() = model?.summary

    private var notify: ((value: Bookmark) -> Unit)? = null

    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: ((value: Bookmark) -> Unit)?) {
        notify = value
    }

    fun onDetailClick(view: View) {
        notify?.let {call ->
            model?.let(call)
        }
    }
}
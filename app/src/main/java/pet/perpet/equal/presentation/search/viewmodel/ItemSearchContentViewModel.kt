package pet.perpet.equal.presentation.search.viewmodel

import android.view.View
import pet.perpet.domain.model.search.SearchArticle

class ItemSearchContentViewModel (var model: SearchArticle? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val title: String?
        get() = model?.top.orEmpty()

    val subTitle: String?
        get() = model?.summary.orEmpty()

    val coverThumbnailUrl: String?
        get() = model?.image.orEmpty()

    private var notify: ((value: SearchArticle) -> Unit)? = null

    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: ((value: SearchArticle) -> Unit)?) {
        notify = value
    }

    fun onDetailClick(view: View) {
        notify?.let {call ->
            model?.let(call)
        }
    }
}
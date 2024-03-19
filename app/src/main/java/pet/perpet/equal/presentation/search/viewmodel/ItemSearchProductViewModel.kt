package pet.perpet.equal.presentation.search.viewmodel

import android.view.View
import pet.perpet.domain.model.search.SearchProduct
import pet.perpet.equal.presentation.goSearchProductDetail

class ItemSearchProductViewModel  (var model: SearchProduct? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val title: String?
        get() = model?.name_kor.orEmpty()

    val subTitle: String?
        get() = model?.summary.orEmpty()

    val coverThumbnailUrl: String?
        get() = model?.image.orEmpty()


    //======================================================================
    // Public Methods
    //======================================================================

    fun onDetailClick(view: View) {
        model?.let {
            view.context.goSearchProductDetail(it)
        }
    }
}
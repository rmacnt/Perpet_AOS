package pet.perpet.equal.presentation.search.viewmodel

import android.view.View
import pet.perpet.data.nonnull
import pet.perpet.domain.model.search.SearchTag

class ItemSearchTagViewModel (var model: SearchTag? = null) {

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
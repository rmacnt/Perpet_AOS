package pet.perpet.equal.presentation.search.viewmodel

import android.view.View
import pet.perpet.data.nonnull
import pet.perpet.domain.model.search.Tag
import pet.perpet.framework.util.Logger

class ItemTagViewModel(var model: Tag? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val name: String?
        get() = model?.name

    private var notify: ((value: String) -> Unit)? = null

    //======================================================================
    // Public Methods
    //======================================================================

    fun notify(value: ((value: String) -> Unit)?) {
        notify = value
    }

    open fun onClick(view: View) {
        Logger.d("ItemTagViewModel", "onClick > ${model}")
        notify?.let {call ->
            call(model?.name.nonnull())
        }
    }
}
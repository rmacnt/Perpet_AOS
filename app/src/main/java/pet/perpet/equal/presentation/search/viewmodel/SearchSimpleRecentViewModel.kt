package pet.perpet.equal.presentation.search.viewmodel

import android.view.View
import pet.perpet.domain.model.search.SearchSimple
import pet.perpet.framework.util.Logger

class SearchSimpleRecentViewModel (var model: SearchSimple? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val name: String?
        get() = model?.text

    //======================================================================
    // Public Methods
    //======================================================================

    open fun onClick(view: View) {
        Logger.d("SearchSimpleViewModel", "onClick > ${model}")
    }
}
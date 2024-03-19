package pet.perpet.equal.presentation.supplement.viewmodel

import android.view.View
import pet.perpet.domain.model.medical.Ingredient

class CodeImageViewModel (var model: Ingredient? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val title: String?
        get() = model?.name


}
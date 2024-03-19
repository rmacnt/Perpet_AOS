package pet.perpet.equal.presentation.supplement.viewmodel

import pet.perpet.domain.model.medical.Ingredient

class CodeViewModel (var model: Ingredient? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val title: String?
        get() = model?.name

    val percent: String?
        get() = "${model?.dosage}mg/kg"


}
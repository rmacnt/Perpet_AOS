package pet.perpet.equal.presentation.search.viewmodel

import pet.perpet.domain.model.search.Ingredient


class ItemCodeViewModel (var model: Ingredient? = null) {

    //======================================================================
    // Variables
    //======================================================================

    val title: String?
        get() = model?.name

    val percent: String?
        get() = "${model?.dosage}mg/kg"


}
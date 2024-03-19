package pet.perpet.equal.presentation.sign.model

data class PetAllergy (
    var allergyId: String? = "",
    var petAsk: Boolean? = true,
    var petAllegi: Int? = -1,
    var petAllegySelect: String = "",
    var error: Boolean = false
)
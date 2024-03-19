package pet.perpet.equal.presentation.sign.model

data class PetBcs (
    var petName: String? = "",
    var petBcsCode: Int? = -1,
    var petSelect: String? = "",
    var petAsk: Boolean? = true,
    var petBcsAllView: Boolean = false,
    var error: Boolean = false
)

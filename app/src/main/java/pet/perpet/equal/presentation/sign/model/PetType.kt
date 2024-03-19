package pet.perpet.equal.presentation.sign.model

data class PetType (
    var petSelect: String? = "",
    var petName: String? = "",
    var petBreedId: Int? = 0,
    var petTypeName: String? = "",
    var petAsk: Boolean = true,
    var error: Boolean = false
)
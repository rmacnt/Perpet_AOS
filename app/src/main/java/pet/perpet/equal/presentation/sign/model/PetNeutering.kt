package pet.perpet.equal.presentation.sign.model

data class PetNeutering (
    var neutralizationCode: Int? = -1,
    var petAsk: Boolean? = true,
    var error: Boolean = false
)
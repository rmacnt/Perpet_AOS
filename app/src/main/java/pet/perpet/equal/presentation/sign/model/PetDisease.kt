package pet.perpet.equal.presentation.sign.model

data class PetDisease (
    var petDisease: Int = -1,
    var diseaseId: String? = null,
    var diseaseSub: HashMap<Int , String > = HashMap(),
    var petAsk: Boolean? = true,
    var error: Boolean? = false
)
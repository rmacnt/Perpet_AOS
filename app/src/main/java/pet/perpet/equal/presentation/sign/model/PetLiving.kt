package pet.perpet.equal.presentation.sign.model

data class PetLiving (
    var mainActPlaceCode: Int? = -1,
    var mainActPlaceEtc: String? = "",
    var petAsk: Boolean? = true,
    var error: Boolean = false,
    var errorMessage: String? = "필수 입력 항목입니다."
)
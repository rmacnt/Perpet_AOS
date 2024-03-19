package pet.perpet.equal.presentation.intakecheck.model

import java.io.Serializable

data class IntakeAlarm (
    var type: Int? = -1,
    var time: Int? = -1,
    var userId: Int? = -1
) : Serializable
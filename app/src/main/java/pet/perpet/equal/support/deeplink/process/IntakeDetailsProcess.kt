package pet.perpet.equal.support.deeplink.process

import android.content.Context
import pet.perpet.equal.presentation.goIntakeCheck
import pet.perpet.equal.support.deeplink.model.BaseArgument

class IntakeDetailsProcess (body: BaseArgument) : DeepLinkProcess(body) {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun observable(context: Context) {
        context.goIntakeCheck()
        finish(context)
    }

}
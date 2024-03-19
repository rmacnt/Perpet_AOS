package pet.perpet.equal.support.deeplink.process

import android.content.Context
import pet.perpet.equal.presentation.goSearch
import pet.perpet.equal.support.deeplink.model.BaseArgument

class NutrientReportDetailProcess (body: BaseArgument) : DeepLinkProcess(body) {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun observable(context: Context) {
        context.goSearch(type = 1)
        finish(context)
    }

}
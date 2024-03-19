package pet.perpet.equal.support.deeplink.process

import android.content.Context
import android.util.Log
import pet.perpet.equal.presentation.goSearch
import pet.perpet.equal.support.deeplink.model.BaseArgument

class SearchDetailsProcess(body: BaseArgument) : DeepLinkProcess(body) {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun observable(context: Context) {

        if (body.keyword.isNotEmpty()) {
            context.goSearch(body.keyword)
        } else {
            context.goSearch()
        }
        finish(context)
    }
}
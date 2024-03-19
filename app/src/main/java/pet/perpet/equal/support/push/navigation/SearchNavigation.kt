package pet.perpet.equal.support.push.navigation

import android.content.Context
import android.net.Uri
import pet.perpet.domain.model.notification.PushList
import pet.perpet.equal.presentation.goSearch

class SearchNavigation  (args: PushList) : Navigation(args) {

    override fun isValid(args: PushList): Boolean {
        return true
    }

    override fun go(context: Context) {
        val keyword = Uri.parse(args.link.orEmpty()).getQueryParameter("keyword")
        keyword?.let {
            context.goSearch(it)
        }
    }
}
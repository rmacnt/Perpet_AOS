package pet.perpet.equal.support.push.navigation

import android.content.Context
import android.net.Uri
import pet.perpet.domain.model.notification.PushList
import pet.perpet.equal.support.deeplink.goDeepLinkProcess
import pet.perpet.equal.support.deeplink.model.Argument

class DeeplinkNavigation(args: PushList) : Navigation(args) {

    override fun isValid(args: PushList): Boolean {
        return args.link.isNullOrEmpty().not()
    }

    override fun go(context: Context) {
        val deepLinkArgument = Argument(Uri.parse(args.link.orEmpty()))
        context.goDeepLinkProcess(deepLinkArgument)
    }
}
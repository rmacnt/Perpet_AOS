package pet.perpet.equal.support.push.navigation

import android.content.Context
import android.content.Intent
import android.net.Uri
import pet.perpet.domain.model.notification.PushList

class ExternalBrowserNavigation (args: PushList) : Navigation(args) {

    override fun isValid(args: PushList): Boolean {
        return true
    }

    override fun go(context: Context) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(args.link.orEmpty()))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intent.setPackage("com.android.chrome")
        context.startActivity(intent)

    }
}
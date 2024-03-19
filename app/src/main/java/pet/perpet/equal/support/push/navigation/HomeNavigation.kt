package pet.perpet.equal.support.push.navigation

import android.content.Context
import pet.perpet.domain.model.notification.PushList
import pet.perpet.equal.presentation.goHome

class HomeNavigation (args: PushList) : Navigation(args) {

    override fun isValid(args: PushList): Boolean {
        return true
    }

    override fun go(context: Context) {
        context.goHome()
    }
}
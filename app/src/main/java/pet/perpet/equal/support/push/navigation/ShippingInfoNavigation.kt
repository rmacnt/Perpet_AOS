package pet.perpet.equal.support.push.navigation

import android.content.Context
import pet.perpet.domain.model.notification.PushList
import pet.perpet.equal.presentation.goShippingManagement

class ShippingInfoNavigation (args: PushList) : Navigation(args) {

    override fun isValid(args: PushList): Boolean {
        return true
    }

    override fun go(context: Context) {
        context.goShippingManagement()
    }
}
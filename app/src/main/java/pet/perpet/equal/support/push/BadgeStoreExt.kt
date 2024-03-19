package pet.perpet.equal.support.push

import androidx.fragment.app.Fragment
import pet.perpet.equal.presentation.base.bus.RxBus2
import pet.perpet.equal.support.logger.AppLogger
import pet.perpet.equal.support.push.model.BadgeCount
import pet.perpet.framework.fragment.safetyCallback

fun BadgeStore.subscribe(fragment: Fragment, callback: (badge: BadgeCount) -> Unit) {
    RxBus2.subscribe(fragment, BadgeCount::class.java) {
        AppLogger.w("PUSH", "subscribe > badge act ${it}")
        fragment.safetyCallback {
            BadgeStore.badgeCount?.let { it1 -> callback(it1) }
        }
    }
}

fun BadgeStore.update() {
    val count = badgeCount?.pushBadgeCount
    count?.let {
        saveBadge(BadgeCount(it + 1))
        RxBus2.post(BadgeCount(it + 1))
    }
}

fun BadgeStore.update(reset: Int) {
    saveBadge(BadgeCount(reset))
    RxBus2.post(BadgeCount(reset))
}


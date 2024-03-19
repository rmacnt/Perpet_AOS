package pet.perpet.equal.support.channeltalk

import androidx.fragment.app.Fragment
import pet.perpet.equal.presentation.base.bus.RxBus2
import pet.perpet.equal.support.channeltalk.model.ChannelTalkBadgeCount
import pet.perpet.framework.fragment.safetyCallback

fun ChannelTalkBadgeStore.subscribe(fragment: Fragment, callback: (channelTalkBadgeCount: ChannelTalkBadgeCount) -> Unit) {
    RxBus2.subscribe(fragment, ChannelTalkBadgeCount::class.java) {
        fragment.safetyCallback {
            ChannelTalkBadgeStore.channelTalkBadgeCount?.let { it1 -> callback(it1) }
        }
    }
}

fun ChannelTalkBadgeStore.update(count: Int) {
    saveChannelTalkBadge(ChannelTalkBadgeCount(count))
    RxBus2.post(ChannelTalkBadgeCount(count))
}
package pet.perpet.equal.support.channeltalk

import pet.perpet.data.repository.base.datasource.CashDataSource
import pet.perpet.equal.support.channeltalk.model.ChannelTalkBadgeCount

object ChannelTalkBadgeStore {

    //======================================================================
    // Variables
    //======================================================================

    val channelTalkBadgeCount: ChannelTalkBadgeCount?
        get() = channelTalkBadgeStore.get()


    private val channelTalkBadgeStore: CashDataSource<ChannelTalkBadgeCount> by lazy {
        object : CashDataSource<ChannelTalkBadgeCount>() {
            override val key: String
                get() = "channelTalkBadgeStore"
        }
    }

    //======================================================================
    // Public Methods
    //======================================================================


    fun saveChannelTalkBadge(badge: ChannelTalkBadgeCount?) {
        channelTalkBadgeStore.clear()
        channelTalkBadgeStore.save(badge)
    }
}
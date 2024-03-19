package pet.perpet.equal.support.push

import pet.perpet.data.repository.base.datasource.CashDataSource
import pet.perpet.equal.support.push.model.BadgeCount

object BadgeStore {
    //======================================================================
    // Variables
    //======================================================================

    val badgeCount: BadgeCount?
        get() = badgeStore.get()

    private val badgeStore: CashDataSource<BadgeCount> by lazy {
        object : CashDataSource<BadgeCount>() {
            override val key: String
                get() = "badgeStore"
        }
    }

    //======================================================================
    // Public Methods
    //======================================================================

    fun clear() {
        badgeStore.clear()
    }

    fun saveBadge(badge: BadgeCount?) {
        badgeStore.save(badge)
    }

    fun clearBadge() {
        badgeStore.clear()
    }
}
package pet.perpet.equal.support.push.model

import com.google.gson.annotations.SerializedName
import pet.perpet.data.nonnull

data class BadgeCount (
    @SerializedName("pushBadgeCount")
    var pushBadgeCount: Int? = 0

)  {
    val isTalkCount: Boolean
        get() = pushBadgeCount.nonnull() > 0
}
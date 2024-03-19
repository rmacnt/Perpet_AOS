package pet.perpet.equal.support.channeltalk.model

import com.google.gson.annotations.SerializedName
import pet.perpet.data.nonnull

data class ChannelTalkBadgeCount(
    @SerializedName("talkCount")
    var talkCount: Int = 0

) {
    val isTalkCount: Boolean
        get() = talkCount.nonnull() > 0

}

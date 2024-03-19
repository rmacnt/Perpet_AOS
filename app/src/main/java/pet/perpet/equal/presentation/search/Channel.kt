package pet.perpet.equal.presentation.search

import androidx.fragment.app.Fragment
import pet.perpet.framework.channel.ChannelLiveData
import pet.perpet.framework.channel.ChannelProviders

object Channel {

    //======================================================================
    // Public Methods
    //======================================================================

    fun keyword(fragment: Fragment?): ChannelLiveData<String>? {
        return ChannelProviders.of(fragment)?.get("keywoard")
    }
}
package pet.perpet.framework.channel

import android.app.Activity
import androidx.fragment.app.Fragment
import pet.perpet.framework.activity.AppCompatActivity
import pet.perpet.framework.fragment.FragmentSupport

object ChannelProviders {

    @Deprecated("bug")
    val global: ChannelProvider by lazy {
        ChannelProvider(ChannelStore())
    }

    fun of(fragment: Fragment?): ChannelProvider? {
        if (fragment is FragmentSupport<*>) {
            return ChannelProvider(fragment.channelStore)
        }
        return null
    }

    fun of(fragment: Activity?): ChannelProvider? {
        if (fragment is AppCompatActivity) {
            return ChannelProvider(fragment.store)
        }
        return null
    }

}
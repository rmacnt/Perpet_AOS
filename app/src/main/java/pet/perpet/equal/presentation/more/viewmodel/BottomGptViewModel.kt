package pet.perpet.equal.presentation.more.viewmodel

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.zoyi.channel.plugin.android.ChannelIO
import com.zoyi.channel.plugin.android.open.config.BootConfig
import com.zoyi.channel.plugin.android.open.enumerate.BootStatus
import com.zoyi.channel.plugin.android.open.listener.ChannelPluginListener
import com.zoyi.channel.plugin.android.open.model.PopupData
import com.zoyi.channel.plugin.android.open.model.Profile
import com.zoyi.channel.plugin.android.open.model.User
import pet.perpet.domain.UserStore
import pet.perpet.equal.MyApplication
import pet.perpet.equal.R
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.support.navigation.showWeb
import pet.perpet.framework.fragment.BottomSheetDialogFragment
import pet.perpet.framework.fragment.UseViewModel

class BottomGptViewModel(fragment: Fragment) : UseViewModel(fragment) {


    //======================================================================
    // Public Methods
    //======================================================================

    fun onServiceClick(view: View) {
        ChannelIO.showMessenger(activity)
    }

    fun onGptClick(view: View) {
        activity?.showWeb("https://app.customgpt.ai/projects/11628/ask-me-anything?shareable_slug=9bec0e9c6be34bc53f4e3d84e82eb9ad&embed=1")
    }

    fun onNegativeClick(view: View) {
        if (fragment is BottomSheetDialogFragment<*>) {
            Bundle().apply {
                this.putBoolean("success", false)
            }.run {
                (fragment as BottomSheetDialogFragment<*>).dismiss(this)
            }
        }
    }

}
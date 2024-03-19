package pet.perpet.equal.presentation.more.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.zoyi.channel.plugin.android.ChannelIO
import com.zoyi.channel.plugin.android.open.config.BootConfig
import com.zoyi.channel.plugin.android.open.enumerate.BootStatus
import com.zoyi.channel.plugin.android.open.listener.ChannelPluginListener
import com.zoyi.channel.plugin.android.open.model.PopupData
import com.zoyi.channel.plugin.android.open.model.Profile
import com.zoyi.channel.plugin.android.open.model.User
import pet.perpet.data.nonnull
import pet.perpet.domain.UserStore
import pet.perpet.equal.R
import pet.perpet.equal.databinding.MoreFragmentBinding
import pet.perpet.equal.presentation.more.viewmodel.MoreViewModel
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.support.channeltalk.ChannelTalkBadgeStore
import pet.perpet.equal.support.channeltalk.subscribe
import pet.perpet.framework.fragment.Fragment

class MoreFragment : Fragment<MoreViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: MoreFragmentBinding

    private var viewPosition = -1

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.observeBindingNotify {
            val indicator = binding.indicator
            val viewPager = binding.viewpager
            viewPager.apply {
                swiping = true
                adapter = viewmodel.fragmentAdapter
                offscreenPageLimit = adapter?.count.nonnull()

                binding.model = viewmodel

                addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) {

                    }

                    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                    }

                    override fun onPageSelected(position: Int) {
                        viewPosition = position
                    }
                })



            }

            indicator.setViewPager(viewPager)
            if(viewPosition != -1)
                viewPager.currentItem = viewPosition
            else {
                viewPager.setCurrentItem(0, true)
            }

        }

        ChannelTalkBadgeStore.subscribe(this) {
            viewmodel.channelTalkCount.value = it.isTalkCount
            binding.model = viewmodel
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.more_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = MoreFragmentBinding.bind(view)
        binding.model = viewmodel
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        viewmodel.getPetList()
    }

}
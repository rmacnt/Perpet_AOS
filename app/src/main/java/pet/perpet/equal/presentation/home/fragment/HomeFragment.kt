package pet.perpet.equal.presentation.home.fragment

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionManager
import android.view.Gravity
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
import pet.perpet.domain.model.main.MainCard
import pet.perpet.domain.model.pet.Pet
import pet.perpet.domain.usecase.pet.PetSelectUseCase
import pet.perpet.equal.MyApplication
import pet.perpet.equal.R
import pet.perpet.equal.databinding.HomeFragmentBinding
import pet.perpet.equal.presentation.home.adapter.MainCardPagerAdapter
import pet.perpet.equal.presentation.home.viewholder.MainItemViewHolder
import pet.perpet.equal.presentation.home.viewmodel.HomeViewModel
import pet.perpet.equal.presentation.intakecheck.IntakeStore
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.presentation.observeBindingNotifyNew
import pet.perpet.equal.presentation.observeBindingNotifyView
import pet.perpet.equal.support.alarm.AlarmReceiver
import pet.perpet.equal.support.channeltalk.ChannelTalkBadgeStore
import pet.perpet.equal.support.channeltalk.update
import pet.perpet.equal.support.push.BadgeStore
import pet.perpet.equal.support.push.subscribe
import pet.perpet.framework.fragment.Fragment
import pet.perpet.framework.fragment.InfinityPagerAdapter
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.OnViewHolderEventListener
import java.util.Calendar

class HomeFragment : Fragment<HomeViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: HomeFragmentBinding

    private var dataList: ArrayList<MainCard> = ArrayList()

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.mainOpen = true
        viewmodel.observeBindingNotify {
            binding.model = viewmodel
        }

        viewmodel.observeBindingNotifyView {
            binding.cardStackView.currentItem = 0
        }
        viewmodel.observeBindingNotifyNew {
            val transition: Transition = Slide(Gravity.TOP)
            transition.addTarget(binding.clPetSelect)
            transition.duration = 400
            TransitionManager.beginDelayedTransition(binding.clLayout, transition)

            binding.model = viewmodel
        }

        val profile = Profile.create()
        val bootConfig = BootConfig.create(getString(R.string.channel_talk))
            .setMemberId(UserStore.userInfo?.id.toString())
            .setProfile(profile)

        viewmodel.observeBindingNotify {
            ChannelIO.boot(bootConfig) { bootStatus: BootStatus, user: User? ->
                ChannelIO.setListener(object : ChannelPluginListener {
                    override fun onShowMessenger() {
                    }

                    override fun onHideMessenger() {
                    }

                    override fun onChatCreated(p0: String?) {
                    }

                    override fun onBadgeChanged(p0: Int) {
                    }
                    override fun onBadgeChanged(unread: Int, alert: Int) {
                        ChannelTalkBadgeStore.update(unread)
                    }

                    override fun onFollowUpChanged(p0: MutableMap<String, String>?) {
                    }

                    override fun onUrlClicked(p0: String?): Boolean {
                        return true
                    }

                    override fun onPushNotificationClicked(p0: String?): Boolean {
                        return true
                    }

                    override fun onPopupDataReceived(p0: PopupData?) {
                    }
                })
            }
        }

        if (ChannelIO.hasStoredPushNotification(activity)) {
            ChannelIO.openStoredPushNotification(activity)
        }

        BadgeStore.subscribe(this) {
            viewmodel.pushTalkCount.value = it.isTalkCount
            binding.model = viewmodel
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = HomeFragmentBinding.bind(view)
        binding.model = viewmodel


        viewmodel.itemPetListDiffer.adapter.setHolderEventListener(object :
            OnViewHolderEventListener {
            override fun onRecyclerViewHolderEvent(
                holder: BaseRecyclerViewHolder<*>,
                id: Int,
                bundle: Bundle,
            ) {

                if (holder is MainItemViewHolder) {
                    val item = holder.item
                    if (item is Pet) {
                        viewmodel.moreResult.value = false
                        item.let {
                            PetSelectUseCase().parameter2 {
                                this.id = it.id?.nonnull()
                            }.success {
                                viewmodel.getPetList {
                                    dataList = it
                                    settingViewPager()
                                }
                            }.fail {
                            }.execute()
                        }
                    }
                }
            }
        })

        if (viewmodel.first.not()) {
            viewmodel.getPetList {
                dataList = it
                settingViewPager()
            }
        }

//        setAlarm()

        super.onViewCreated(view, savedInstanceState)
    }


    private fun settingViewPager() {
        activity?.let { activity ->
            val wrappedAdapter = InfinityPagerAdapter(
                MainCardPagerAdapter(dataList, activity)
            )

            binding.cardStackView.adapter = wrappedAdapter
            binding.cardStackView.run {
                clipToPadding = false
                offscreenPageLimit = 3

                setPageTransformer(true, ViewPagerCardTransformer())
                addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int,
                    ) {
                    }

                    override fun onPageSelected(position: Int) {
                        binding.indicator.selectDot(position)
                    }

                    override fun onPageScrollStateChanged(statSe: Int) {
                    }
                })
            }
            binding.indicator.createDotPanel(
                dataList.size,
                R.drawable.indicator_black_off,
                R.drawable.indicator_black_on,
                0
            )

        }
    }

    class ViewPagerCardTransformer : ViewPager.PageTransformer {
        override fun transformPage(page: View, position: Float) {
            if (position >= 0) {
                page.scaleX = 0.9f - 0.05f * position
                page.scaleY = 1f - 0.05f * position
                page.alpha = 1f - 0.3f * position
                page.translationX = (-page.width + 80) * position
            } else {
                page.alpha = 1 + 0.3f * position
                page.scaleX = 1f + 0.05f * position
                page.scaleY = 1f + 0.05f * position
                page.translationX = 80 * position
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (viewmodel.first &&   MyApplication.contentDetail?.not().nonnull()) {
            viewmodel.getPetList {
                dataList = it
                settingViewPager()
                MyApplication.contentDetail = false
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        MyApplication.mainOpen = false
    }
}
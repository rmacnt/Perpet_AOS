package pet.perpet.equal.presentation.more.viewmodel

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.MutableLiveData
import pet.perpet.domain.usecase.pet.PetListUseCase
import pet.perpet.equal.presentation.base.factory.FragmentItem
import pet.perpet.equal.presentation.base.factory.FragmentMoreItem
import pet.perpet.equal.presentation.goHome
import pet.perpet.equal.presentation.goIntakeCheck
import pet.perpet.equal.presentation.goMypage
import pet.perpet.equal.presentation.goOrderHistory
import pet.perpet.equal.presentation.goPagerWeight
import pet.perpet.equal.presentation.goPayment
import pet.perpet.equal.presentation.goServiceCenter
import pet.perpet.equal.presentation.goSetting
import pet.perpet.equal.presentation.goShippingManagement
import pet.perpet.equal.presentation.more.fragment.MoreItemCardFragment
import pet.perpet.equal.presentation.more.fragment.MoreItemCardInsertFragment
import pet.perpet.equal.support.channeltalk.ChannelTalkBadgeStore
import pet.perpet.equal.support.navigation.showGptAlert
import pet.perpet.framework.fragment.UseViewModel

class MoreViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    var result: Boolean = false

    val subscription: MutableLiveData<Boolean> = MutableLiveData(false)

    val fragmentItems: ArrayList<Any> = arrayListOf()

    val fragmentAdapter by lazy {
        object : FragmentStatePagerAdapter(
            fragment.childFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
            override fun getCount(): Int {
                return fragmentItems.size
            }

            override fun getItem(position: Int): Fragment {
                return if(fragmentItems[position] is FragmentItem) {
                    (fragmentItems[position] as FragmentItem).createFragment()
                } else {
                    (fragmentItems[position] as FragmentMoreItem).createFragment()
                }
            }
        }
    }

    val channelTalkCountResult: Boolean?
        get() = channelTalkCount.value

    val channelTalkCount: MutableLiveData<Boolean> = MutableLiveData(ChannelTalkBadgeStore.channelTalkBadgeCount?.isTalkCount)


    //======================================================================
    // Public Methods
    //======================================================================


    fun onPetGptClick(view: View) {
        if(subscription.value == true) {
            activity?.showGptAlert()
        } else {
            Toast.makeText(activity , "Pet-GPT 서비스는 영양제 구독하셔야 사용 가능합니다." , Toast.LENGTH_SHORT).show()
        }

    }

    fun onHomeClick(view: View) {
        context?.let {
            it.goHome()
        }
    }

    fun onIntakeClick(view: View) {
        context?.let {
            it.goIntakeCheck()
        }
    }


    fun onFinishClick(view: View) {
        activity?.let {
            it.finish()
        }
    }

    fun onMyPageClick(view: View) {
        context?.let {
            it.goMypage()
        }
    }

    fun onServiceClick(view: View) {
        context?.let {
            it.goServiceCenter()
        }
    }

    fun onShippingClick(view: View) {
        context?.let {
            it.goShippingManagement()
        }
    }

    fun onSubscribeClick(view: View) {
        context?.let {
            it.goOrderHistory()
        }
    }


    fun onSettingClick(view: View) {
        context?.let {
            it.goSetting()
        }
    }

    fun onPaymentClick(view: View) {
        context?.let {
            it.goPayment()
        }
    }

    fun getPetList() {
        PetListUseCase().parameter2 {
        }.success {
            it?.let {
                fragmentItems.clear()
                it.content?.let { data->
                    data.forEach {

                        if(it.has_subscription == true) {
                            subscription.value = true
                        }
                        fragmentItems.add(
                            FragmentMoreItem(it, MoreItemCardFragment::class.java)
                        )
                    }
                }

                fragmentItems.add(FragmentItem(0, MoreItemCardInsertFragment::class.java))
                executeViewBinding()

            }
        }.fail {
            context?.goPagerWeight()
        }.execute()
    }


}
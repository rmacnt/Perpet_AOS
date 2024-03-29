package pet.perpet.equal.presentation.health.viewmodel

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import pet.perpet.equal.R
import pet.perpet.equal.presentation.base.factory.FragmentItem
import pet.perpet.equal.presentation.finishAll
import pet.perpet.equal.presentation.goHeathStart
import pet.perpet.equal.presentation.goHome
import pet.perpet.equal.presentation.health.fragment.HealthInfoPagerFragment
import pet.perpet.framework.fragment.UseViewModel

class HealthInfoViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    val time: String = String.format(getString(R.string.paperweight_comment29) , 3)

    private val fragmentItems = mutableListOf(
        FragmentItem(
            FragmentItem.id_intro_1,
            HealthInfoPagerFragment::class.java
        ),
        FragmentItem(
            FragmentItem.id_intro_2,
            HealthInfoPagerFragment::class.java
        ),
        FragmentItem(
            FragmentItem.id_intro_3,
            HealthInfoPagerFragment::class.java
        )
    )

    val fragmentAdapter by lazy {
        object : FragmentStatePagerAdapter(
            fragment.childFragmentManager,
            BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
        ) {
            override fun getCount(): Int {
                return fragmentItems.size
            }

            override fun getItem(position: Int): Fragment {
                return fragmentItems[position].createFragment()
            }
        }
    }


    //======================================================================
    // Public Methods
    //======================================================================

    fun onSubmitClick(view: View) {
        context?.let {
            it.goHeathStart()
        }
    }

    fun onHomeClick(view: View) {
        activity?.let {
            it.finishAll()
            it.goHome()
        }

    }


}
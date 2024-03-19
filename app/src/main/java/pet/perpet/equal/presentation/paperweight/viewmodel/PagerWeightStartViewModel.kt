package pet.perpet.equal.presentation.paperweight.viewmodel

import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import pet.perpet.data.nonnull
import pet.perpet.equal.R
import pet.perpet.equal.presentation.base.factory.FragmentItem
import pet.perpet.equal.presentation.base.ui.AppAlertDialog
import pet.perpet.equal.presentation.createStartActivityIntent
import pet.perpet.equal.presentation.goSign
import pet.perpet.equal.presentation.logout
import pet.perpet.equal.presentation.paperweight.fragment.PagerWeightIntroFragment
import pet.perpet.equal.presentation.paperweight.fragment.PaperWeightStartFragmentArgs
import pet.perpet.framework.fragment.UseViewModel

class PagerWeightStartViewModel(fragment: Fragment) : UseViewModel(fragment) {

    //======================================================================
    // Private Variables
    //======================================================================

    val args by lazy {
        PaperWeightStartFragmentArgs.fromBundle(arguments)
    }

    val newUser: Boolean?
        get() = args.user

    val time: String = String.format(getString(R.string.paperweight_comment29), 1)

    var comment: String = getString(R.string.paperweight_comment23)

    private val fragmentItems = mutableListOf(
        FragmentItem(
            FragmentItem.id_intro_1,
            PagerWeightIntroFragment::class.java
        ),
        FragmentItem(
            FragmentItem.id_intro_2,
            PagerWeightIntroFragment::class.java
        ),
        FragmentItem(
            FragmentItem.id_intro_3,
            PagerWeightIntroFragment::class.java
        ),
        FragmentItem(
            FragmentItem.id_intro_4,
            PagerWeightIntroFragment::class.java
        ),
        FragmentItem(
            FragmentItem.id_intro_5,
            PagerWeightIntroFragment::class.java
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
        activity?.let {
            if(newUser.nonnull().not()) {
                it.finish()
            }
            it.goSign()
        }
    }

    fun onHomeClick(view: View?) {

        if(newUser.nonnull().not()) {
            activity?.finish()
        } else {
            activity?.let {activity->
                AppAlertDialog(
                    activity,
                    getString(R.string.dialog_title),
                    getString(R.string.sign_error),
                    getString(R.string.sign_comment_101),
                    getString(R.string.sign_comment_102)

                ).show(
                    onClickNegative = {
                        activityFinish()
                        logout {
                            val intent = createStartActivityIntent(activity)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            activity.startActivity(intent)
                            activityFinish()
                        }
                    },
                    onClickPositive = {
                        it.dismiss()
                    }

                )
            }
        }

    }
}
package pet.perpet.equal.presentation.health.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pet.perpet.data.nonnull
import pet.perpet.domain.UserStore
import pet.perpet.equal.R
import pet.perpet.equal.databinding.HealthInfoResultFragmentBinding
import pet.perpet.equal.presentation.base.ui.AppAlertDialog
import pet.perpet.equal.presentation.goHome
import pet.perpet.equal.presentation.health.viewmodel.HealthInfoResultViewModel
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.framework.fragment.Fragment

class HealthInfoResultFragment : Fragment<HealthInfoResultViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    private var resultCount: Int = 0
    lateinit var binding: HealthInfoResultFragmentBinding

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.observeBindingNotify {
            binding.model = viewmodel
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.health_info_result_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = HealthInfoResultFragmentBinding.bind(view)
        binding.model = viewmodel

        val indicator = binding.indicator
        val viewPager = binding.viewpager
        viewPager.apply {
            swiping = true
            adapter = viewmodel.fragmentAdapter
            offscreenPageLimit = adapter?.count.nonnull()
        }

        indicator.setViewPager(viewPager)
        viewPager.setCurrentItem(0, true)

        val hander = Handler()
        hander.postDelayed({
            medicalResultPet()
        }, 10000)


        super.onViewCreated(view, savedInstanceState)

    }

    private fun medicalResultPet() {
        viewmodel.getPetInfo {
            if (it.latest_medical_id != null) {
                viewmodel.selectPet = it
                viewmodel.result = true
                binding.tvResult.text = getString(R.string.health_comment14)
                binding.model = viewmodel
            } else {
                if(resultCount >= 6) {

                    activity?.let {activity->
                        AppAlertDialog(
                            activity,
                            getString(R.string.dialog_title),
                            getString(R.string.sign_error_3),
                            getString(R.string.app_dialog_action_confirm)

                        ).show(
                            onClickPositive = {
                                it.dismiss()
                                activity.goHome()
                                activity.finish()
                            }
                        )
                    }
                } else {
                    val hander = Handler()
                    hander.postDelayed({
                        resultCount++
                        medicalResultPet()
                    }, 3000)
                }

            }
        }
    }

    override fun onBackPressed(): Boolean {
        viewmodel.onHomeClick(null)
        return false
    }

}
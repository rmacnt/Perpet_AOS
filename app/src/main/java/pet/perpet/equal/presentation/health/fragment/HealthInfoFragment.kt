package pet.perpet.equal.presentation.health.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pet.perpet.data.nonnull
import pet.perpet.equal.R
import pet.perpet.equal.databinding.HealthInfoFragmentBinding
import pet.perpet.equal.presentation.health.viewmodel.HealthInfoViewModel
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.framework.fragment.Fragment

class HealthInfoFragment : Fragment<HealthInfoViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: HealthInfoFragmentBinding

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
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.health_info_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = HealthInfoFragmentBinding.bind(view)
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

        super.onViewCreated(view, savedInstanceState)

    }

}
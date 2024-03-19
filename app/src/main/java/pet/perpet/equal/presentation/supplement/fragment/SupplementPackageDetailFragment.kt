package pet.perpet.equal.presentation.supplement.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import pet.perpet.equal.R
import pet.perpet.equal.databinding.SupplementPackageDetailFragmentBinding
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.presentation.supplement.viewmodel.SupplementPackageDetailViewModel
import pet.perpet.framework.fragment.Fragment
import pet.perpet.framework.fragment.OnTabSelectedBehavior

class SupplementPackageDetailFragment : Fragment<SupplementPackageDetailViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: SupplementPackageDetailFragmentBinding

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
        return inflater.inflate(R.layout.supplement_package_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = SupplementPackageDetailFragmentBinding.bind(view)
        binding.model = viewmodel

        var fragmentAdapter = viewmodel.fragmentAdapter
        binding.viewpager.run {
            offscreenPageLimit = 2
            adapter = fragmentAdapter
            isSaveEnabled = false
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {

                }

                override fun onPageSelected(position: Int) {
                }

            })
        }

        binding.tablayout.run {
            val pager = binding.viewpager
            setupWithViewPager(pager)
            addOnTabSelectedListener(object : OnTabSelectedBehavior() {
                override fun dependencyView(): ViewPager {
                    return pager
                }
            })
        }

        super.onViewCreated(view, savedInstanceState)

    }

}
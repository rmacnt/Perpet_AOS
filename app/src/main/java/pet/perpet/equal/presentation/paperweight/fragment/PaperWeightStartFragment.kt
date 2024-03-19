package pet.perpet.equal.presentation.paperweight.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import pet.perpet.data.nonnull
import pet.perpet.equal.R
import pet.perpet.equal.databinding.PaperWeightStartFragmentBinding
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.presentation.paperweight.viewmodel.PagerWeightStartViewModel
import pet.perpet.framework.fragment.Fragment


class PaperWeightStartFragment : Fragment<PagerWeightStartViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: PaperWeightStartFragmentBinding

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

        return inflater.inflate(R.layout.paper_weight_start_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = PaperWeightStartFragmentBinding.bind(view)
        binding.model = viewmodel

        val indicator = binding.indicator
        val viewPager = binding.viewpager
        viewPager.apply {
            swiping = true
            adapter = viewmodel.fragmentAdapter
            offscreenPageLimit = adapter?.count.nonnull()
        }
        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                context?.let {
                    viewmodel.comment = when(position) {
                        4-> it.getString(R.string.paperweight_comment27)
                        0-> it.getString(R.string.paperweight_comment23)
                        1-> it.getString(R.string.paperweight_comment24)
                        2-> it.getString(R.string.paperweight_comment25)
                        3-> it.getString(R.string.paperweight_comment26)
                        else -> it.getString(R.string.paperweight_comment23)

                    }
                    viewmodel.executeViewBinding()
                }
            }
            override fun onPageScrollStateChanged(state: Int) {}
        })

        indicator.setViewPager(viewPager)
        viewPager.setCurrentItem(0, true)

        binding.tvSuccess.animate()
            .translationY(view.height.toFloat())
            .alpha(0.0f)
            .setDuration(3000)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    binding.tvSuccess.visibility = View.GONE
                }
            })

        super.onViewCreated(view, savedInstanceState)

    }

    override fun onBackPressed(): Boolean {
        viewmodel.onHomeClick(null)
        return false
    }

}
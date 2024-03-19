package pet.perpet.equal.presentation.examination.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import pet.perpet.data.nonnull
import pet.perpet.equal.R
import pet.perpet.equal.databinding.ExaminationResultDetailFragmentBinding
import pet.perpet.equal.presentation.examination.viewmodel.ExaminationResultDetailViewModel
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.framework.fragment.Fragment
import pet.perpet.framework.fragment.OnTabSelectedBehavior

class ExaminationResultDetailFragment : Fragment<ExaminationResultDetailViewModel>() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: ExaminationResultDetailFragmentBinding

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
        return inflater.inflate(R.layout.examination_result_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding = ExaminationResultDetailFragmentBinding.bind(view)
        binding.model = viewmodel

        viewmodel.args.medical?.results?.let {
            var fragmentAdapter = viewmodel.fragmentAdapter
            binding.viewpager.run {
                offscreenPageLimit = viewmodel.args.medical?.results?.size.nonnull()
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
        }

        super.onViewCreated(view, savedInstanceState)

    }

}
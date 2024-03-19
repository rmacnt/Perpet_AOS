package pet.perpet.equal.presentation.start.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.viewpager.widget.ViewPager
import pet.perpet.equal.R
import pet.perpet.equal.databinding.StartActivityBinding
import pet.perpet.equal.presentation.observeBindingNotify
import pet.perpet.equal.presentation.start.viewmodel.StartViewModel
import pet.perpet.framework.fragment.Fragment
import pet.perpet.framework.nonnull

//class StartFragment : Fragment<StartViewModel>() {
//
//    //======================================================================
//    // Variables
//    //======================================================================
//
//    lateinit var binding: StartActivityBinding
//
//    //======================================================================
//    // Override Methods
//    //======================================================================
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        viewmodel.observeBindingNotify {
////            binding.model = viewmodel
////        }
//        activity?.let { it->
//            it.window.statusBarColor = ContextCompat.getColor(it, R.color.black)
//            WindowCompat.getInsetsController(it.window, it.window.decorView).isAppearanceLightStatusBars = false
//            viewmodel.dependency = {
//                it
//            }
//
//            viewmodel.onCreate(it)
//        }
//
//
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(
//            R.layout.start_activity,
//            container,
//            false
//        )
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        binding = StartActivityBinding.bind(view)
//        binding.model = viewmodel
//
//        val indicator = binding.indicator
//        val viewPager = binding.viewpager
//        viewPager.apply {
//            swiping = true
//            adapter = viewmodel.fragmentAdapter
//            offscreenPageLimit = adapter?.count.nonnull()
//            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//                override fun onPageScrolled(
//                    position: Int,
//                    positionOffset: Float,
//                    positionOffsetPixels: Int
//                ) {
//                }
//
//                override fun onPageSelected(position: Int) {
//                    context?.let {
//                        when(position) {
//                            0-> binding.ivSplash.setImageResource(R.drawable.splash_img_1)
//                            1-> binding.ivSplash.setImageResource(R.drawable.splash_img_2)
//                            2-> binding.ivSplash.setImageResource(R.drawable.splash_img_3)
//                        }
//                        viewmodel.executeViewBinding()
//                    }
//                }
//                override fun onPageScrollStateChanged(state: Int) {}
//            })
//        }
//
//
//        indicator.setViewPager(viewPager)
//        viewPager.setCurrentItem(0, true)
//
//        super.onViewCreated(view, savedInstanceState)
//
//    }
//}
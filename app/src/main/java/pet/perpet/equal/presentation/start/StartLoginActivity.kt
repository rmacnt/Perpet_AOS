package pet.perpet.equal.presentation.start

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import pet.perpet.equal.R
import pet.perpet.equal.databinding.StartActivityBinding
import pet.perpet.equal.presentation.start.viewmodel.StartViewModel
import pet.perpet.framework.activity.AppCompatActivity
import pet.perpet.framework.fragment.ViewModelProviders
import pet.perpet.framework.nonnull

class StartLoginActivity : AppCompatActivity() {

    //======================================================================
    // Variables
    //======================================================================

    lateinit var binding: StartActivityBinding

    private val viewmodel: StartViewModel
        get() = ViewModelProviders.of(this).get(StartViewModel::class.java)


    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.dependency = {
            this
        }
        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = false

        binding = DataBindingUtil.setContentView(this, R.layout.start_activity)
        binding.model = viewmodel
        viewmodel.onCreate(this)

        val indicator = binding.indicator
        val viewPager = binding.viewpager
        viewPager.apply {
            swiping = true
            adapter = viewmodel.fragmentAdapter
            offscreenPageLimit = adapter?.count.nonnull()
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    context?.let {
                        when(position) {
                            0-> binding.ivSplash.setImageResource(R.drawable.splash_img_1)
                            1-> binding.ivSplash.setImageResource(R.drawable.splash_img_2)
                            2-> binding.ivSplash.setImageResource(R.drawable.splash_img_3)
                        }
                        binding.model = viewmodel
                    }
                }
                override fun onPageScrollStateChanged(state: Int) {}
            })
        }


        indicator.setViewPager(viewPager)
        viewPager.setCurrentItem(0, true)

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.run {
            viewmodel.onNewIntent(intent)
        }
    }

}
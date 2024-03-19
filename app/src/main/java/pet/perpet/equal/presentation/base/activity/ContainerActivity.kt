package pet.perpet.equal.presentation.base.activity

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import pet.perpet.equal.R
import pet.perpet.equal.presentation.base.ActivityNavigation
import pet.perpet.framework.activity.AppCompatActivity
import pet.perpet.framework.fragment.FragmentSupport
import pet.perpet.framework.fragment.StringUtil

open class ContainerActivity : AppCompatActivity() {

    //======================================================================
    // Variables
    //======================================================================

    var targetFragment: Fragment? = null
        private set

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_container_activity)
        setStatusBarColor(
            window,
            ContextCompat.getColor(this, android.R.color.transparent),
            true
        )
        if (savedInstanceState == null) {
            val className = intent.getStringExtra(ActivityNavigation.CONTAINER_FRAGMENT_CLASS_NAME).orEmpty()
            if (StringUtil.isEmpty(className) == true) {
                finish()
                return
            }

            var arguments: Bundle? =
                intent.getBundleExtra(ActivityNavigation.CONTAINER_FRAGMENT_BUNDLE)
            if (arguments == null) {
                arguments = Bundle()
            }

            val fragment = Fragment.instantiate(this, className, arguments)
            replace(fragment)
            targetFragment = fragment
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        if (fragment != null && fragment is FragmentSupport<*>) {
            val press = fragment.onBackPressed()
            if (press == true) {
                super.onBackPressed()
            } else {
                return
            }
        }
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        targetFragment = null
    }

    //======================================================================
    // Public Methods
    //======================================================================

    fun attach(fragment: Fragment?) {
        if (fragment == null) return
        supportFragmentManager.beginTransaction()
            .attach(fragment)
            .commit()
    }

    fun detach(fragment: Fragment?) {
        if (fragment == null) return
        supportFragmentManager.beginTransaction()
            .detach(fragment)
            .commit()
    }

    fun replace(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }

    fun setStatusBarColor(window: Window, color: Int, light: Boolean = false) {
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 4.4
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 5.0
            /*window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE*/
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color
        }
        if (light) {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        } else {
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }

    }
}
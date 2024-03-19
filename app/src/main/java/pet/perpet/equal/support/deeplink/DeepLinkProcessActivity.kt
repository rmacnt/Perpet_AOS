package pet.perpet.equal.support.deeplink

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import pet.perpet.equal.support.deeplink.process.DeepLinkProcessFactory
import pet.perpet.framework.activity.AppCompatActivity

class DeepLinkProcessActivity : AppCompatActivity() {

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overviewStatus(window)

        val body = DeepLink.getParameter(intent)
        if (body != null) {
            val instance = DeepLinkProcessFactory.create(body)
            if (instance == null) {
                finish()
            } else {
                instance.process(this)
            }
        } else {
            finish()
        }
    }

    fun overviewStatus(window: Window, light: Boolean = false) {

        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT

        if (light) {
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        } else {
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
        }
    }
}
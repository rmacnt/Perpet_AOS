package pet.perpet.equal.support.push.presentation

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import pet.perpet.data.nonnull
import pet.perpet.equal.support.logger.AppLogger
import pet.perpet.equal.support.push.getLaunch
import pet.perpet.equal.support.push.getMessageBody

class PushLinkActivity : AppCompatActivity() {

    //======================================================================
    // Variables
    //======================================================================

    var launch: Boolean = false

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overviewStatus(window)
        val body = intent?.getMessageBody()
        launch = intent?.getLaunch().nonnull()
        AppLogger.w(AppLogger.Tag.PUSH, "push[${body?.type}] body > $body")
        if (body != null) {
            PushProcessFactory.create(body).run {
                if (this == null) {
                    finish()
                } else {
                    AppLogger.w(AppLogger.Tag.PUSH, "push process > $this")
                    this.preActivitySplash = launch
                    this.process(this@PushLinkActivity)
                }
            }
        } else {
            finish()
        }
    }

    fun overviewStatus(window: Window, light: Boolean = false) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // 4.4
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 5.0
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = Color.TRANSPARENT
        }

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
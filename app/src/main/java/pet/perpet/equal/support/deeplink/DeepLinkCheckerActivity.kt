package pet.perpet.equal.support.deeplink

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import pet.perpet.domain.TokenStore
import pet.perpet.domain.UserStore
import pet.perpet.equal.AppConfig
import pet.perpet.equal.MyApplication
import pet.perpet.equal.presentation.createStartActivityIntent
import pet.perpet.equal.presentation.start.SplashActivity
import pet.perpet.equal.support.deeplink.model.ArgumentFactory
import pet.perpet.equal.support.deeplink.model.BaseArgument
import pet.perpet.equal.support.logger.AppLogger
import pet.perpet.framework.activity.AppCompatActivity

class DeepLinkCheckerActivity  : AppCompatActivity() {

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

        val startActivity = findActivity(SplashActivity::class.java)

        val uri = intent?.data
        if (AppConfig.LOG_ENABLE) {
            uri?.log()
        }

        AppLogger.w(
            AppLogger.Tag.DEEP_LINK,
            "isUseUser : ${UserStore.isUseUser}, token : ${TokenStore.isUseToken}"
        )

        val isNotLogin: Boolean = UserStore.isUseUser.not() || TokenStore.isUseToken.not()
        val deepLinkArgument = if (uri != null) {
            ArgumentFactory.create(uri)
        } else {
            null
        }
        if (isNotLogin) {
            if (startActivity != null) {
                goStart(deepLinkArgument)
            } else {
                goSplash(deepLinkArgument)
            }
        } else if (MyApplication.mainOpen == false) {
            goSplash(deepLinkArgument)
        } else {
            deepLinkArgument?.let { goDeepLinkProcess(it) }
        }
        finish()
    }

    //======================================================================
    // Private Methods
    //======================================================================

    private fun findActivity(target: Class<*>): Activity? {
        return this.applicationContext?.run {
            if (this is MyApplication) {
                this
            } else {
                null
            }
        }?.run {
            findActivity(target)
        }
    }

    private fun goSplash(argument: BaseArgument?) {
        val i = Intent(this, SplashActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        argument?.let { DeepLink.saveParameter(i, it) }
        startActivity(i)
    }

    private fun goStart(argument: BaseArgument?) {
        val i = createStartActivityIntent(this)
        i.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        argument?.let { DeepLink.saveParameter(i, it) }
        startActivity(i)
    }

    private fun Uri.log() {
        val scheme = scheme.orEmpty()
        AppLogger.w(
            AppLogger.Tag.DEEP_LINK,
            "full url : $this"
        )
        AppLogger.w(
            AppLogger.Tag.DEEP_LINK,
            "scheme : $scheme"
        )
        AppLogger.w(
            AppLogger.Tag.DEEP_LINK,
            "pull path : $path"
        )
        pathSegments?.forEach {
            AppLogger.w(
                AppLogger.Tag.DEEP_LINK,
                "path : $it"
            )
        }
        queryParameterNames?.forEach {
            AppLogger.w(
                AppLogger.Tag.DEEP_LINK,
                "query : $it, value : ${getQueryParameter(it)}"
            )
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
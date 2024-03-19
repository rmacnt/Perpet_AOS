package pet.perpet.equal.presentation.start

import android.Manifest
import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.airbnb.lottie.LottieAnimationView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pet.perpet.data.nonnull
import pet.perpet.domain.TokenStore
import pet.perpet.domain.UserStore
import pet.perpet.domain.UserStore.mainPetSync
import pet.perpet.domain.usecase.pet.PetListUseCase
import pet.perpet.equal.R
import pet.perpet.equal.presentation.checkPermissionCall
import pet.perpet.equal.presentation.createMainScope
import pet.perpet.equal.presentation.getLogout
import pet.perpet.equal.presentation.goHome
import pet.perpet.equal.presentation.goPagerWeight
import pet.perpet.equal.presentation.goStart
import pet.perpet.equal.presentation.safetyShow
import pet.perpet.equal.presentation.start.viewmodel.ErrorState
import pet.perpet.equal.presentation.start.viewmodel.TokenCheckViewModel
import pet.perpet.equal.support.deeplink.DeepLink
import pet.perpet.equal.support.deeplink.goDeepLinkProcess
import pet.perpet.equal.support.deeplink.model.BaseArgument
import pet.perpet.equal.support.logger.AppLogger
import pet.perpet.equal.support.logger.d
import pet.perpet.equal.support.logger.w
import pet.perpet.equal.support.push.goPushMessage
import pet.perpet.equal.support.push.model.PushMessage
import pet.perpet.framework.activity.AppCompatActivity
import pet.perpet.framework.fragment.ViewModelProviders

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    //======================================================================
    // Variables
    //======================================================================

    val minAnim = 0

    var currentTime: Long = 0

    val viewmodel: TokenCheckViewModel by lazy {
        ViewModelProviders.of(this).get(TokenCheckViewModel::class.java)
    }

    var deeplink: BaseArgument? = null

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.statusBarColor = ContextCompat.getColor(this, R.color.black)
        WindowCompat.getInsetsController(window, window.decorView).isAppearanceLightStatusBars = false

        setContentView(R.layout.activity_splash)
        deeplink = DeepLink.getParameter(intent)
        val bundle = intent.extras

        AppLogger.Tag.DEEP_LINK.w(
            "SplashActivity > intent : ${intent}, bundle : $bundle type : ${
                intent.extras?.getString(
                    "type"
                )
            }, body : ${
                intent.extras?.getString(
                    "body"
                )
            }"
        )

        val lottie = findViewById<LottieAnimationView>(R.id.lottie_view)
        lottie.addAnimatorListener(object : Animator.AnimatorListener {

            override fun onAnimationStart(animation: Animator) {}
            override fun onAnimationEnd(animation: Animator) {
                if (isTaskRoot.not()) {
                    if (bundle != null || bundle?.isEmpty == false) {
                        check()
                        return
                    }

                    val intent = intent
                    val action = intent.action
                    if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action != null && action == Intent.ACTION_MAIN) {
                        AppLogger.w(
                            AppLogger.Tag.APP_CONFIG,
                            "Main Activity is not the root.  Finishing Main Activity instead of launching."
                        )
                        finish()
                        return
                    }
                }
                currentTime = System.currentTimeMillis()

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    checkPermission(this@SplashActivity) {
                        check()
                    }
                } else {
                    check()
                }
            }
            override fun onAnimationCancel(animation: Animator) {}
            override fun onAnimationRepeat(animation: Animator) {}
        })

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        deeplink = DeepLink.getParameter(intent)
        AppLogger.w(AppLogger.Tag.DEEP_LINK, "onNewIntent > deeplink : $deeplink")
    }

    //======================================================================
    // Private Methods
    //======================================================================

    private fun check() {

        if (!TokenStore.isUseToken) {
            val now = minAnim - (System.currentTimeMillis() - currentTime)
            createMainScope().launch {
                delay(now)
                competeNoLogin()
            }
            return
        }
        viewmodel.check(
            fail = {
                when (it) {
                    ErrorState.not_user_info, ErrorState.not_token_info -> {
                        getLogout {
                            competeNoLogin()
                        }
                    }
                    ErrorState.internal_exception -> {
                        val now = minAnim - (System.currentTimeMillis() - currentTime)
                        createMainScope().launch {
                            delay(now)
                            competeNoLogin()
                        }
                    }
                    ErrorState.not_connetion -> {
                        try {
                            getNetworkIssue().safetyShow()
                        } catch (e: java.lang.Exception) {
                            e.printStackTrace()
                        }
                    }

                    else ->{}
                }
            },
            success = {
                val user = UserStore.user
                val now = minAnim - (System.currentTimeMillis() - currentTime)
                createMainScope().launch {
                    delay(now)
                    if(user != null && user.signUp == false) {
                        getMyPetList()
                    } else {
                        goStart(this@SplashActivity, deeplink)
                        finish()
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
                    }


                }
            })
    }

    private fun completeValidAuth() {
        val type = intent?.extras?.getString("type")?.run {
            PushMessage.Type.parseString(this)
        }
        val body = intent?.extras?.getString("body")
        AppLogger.w(AppLogger.Tag.PUSH, "completeValidAuth > type : ${type}, body : $body")
        if (type != null && body != null) {
            AppLogger.w(AppLogger.Tag.PUSH, "completeValidAuth > goMessageDeepLink")
            this.goHome()
            this.goPushMessage(type, body)
        } else {
            val link = deeplink
            if (link != null) {
                this.goHome()
                this.goDeepLinkProcess(link)
            } else {
                this.goHome()
            }
        }
        finish()

        overridePendingTransition(R.anim.fadein, R.anim.fadeout)
    }

    private fun competeNoLogin() {
        val user = UserStore.user
        val now = minAnim - (System.currentTimeMillis() - currentTime)
        createMainScope().launch {
            delay(now)
            if(user != null && user.signUp == false) {
                getMyPetList()
            } else {
                AppLogger.w(AppLogger.Tag.DEEP_LINK, "competeNoLogin > deeplink : $deeplink")
                goStart(this@SplashActivity, deeplink)
                finish()
                overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            }


        }
    }

    private fun getMyPetList() {
        PetListUseCase().parameter2 {
        }.success {
            it?.let {
                it.content?.forEach {
                    if(it.main_yn == "Y") {
                        mainPetSync(it)
                    }
                }
                if((it.content?.size ?: -1) > 0) {
                    completeValidAuth()
                } else {
                    goPagerWeight(true)
                    finish()
                }
            } ?: run {
                goStart(this@SplashActivity, deeplink)
                finish()
                overridePendingTransition(R.anim.fadein, R.anim.fadeout)
            }
        }.fail {
            goPagerWeight(true)
            finish()
        }.execute()


    }

    private fun getNetworkIssue(): AlertDialog {
        val builder = AlertDialog.Builder(this)
            .setTitle(getString(R.string.app_dialog_title_error))
            .setMessage(getString(R.string.app_dialog_message_network_connection_issue))
            .setCancelable(false)
            .setPositiveButton(getString(R.string.app_dialog_action_confirm)) { dialog, which ->
                finish()
            }
        return builder.create()
    }

    private fun checkPermission(activity: AppCompatActivity , callback: (success: Boolean) -> Unit) {

        var array : ArrayList<String> = arrayListOf()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            array = arrayListOf(
                Manifest.permission.POST_NOTIFICATIONS,
                Manifest.permission.SCHEDULE_EXACT_ALARM
            )
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            array = arrayListOf(
                Manifest.permission.SCHEDULE_EXACT_ALARM
            )
        }

        activity.checkPermissionCall(
            array
        ) { grants: BooleanArray?, showRequestPermissions: BooleanArray? ->
            val permission =
                grants?.all { it }.nonnull()

            AppLogger.Tag.DOWNLOAD.d(
                "checkPermission > permission : ${permission}"
            )
            callback(permission)
        }
    }
}
package pet.perpet.equal

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import coil.Coil
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.facebook.drawee.backends.pipeline.Fresco
import com.kakao.sdk.common.KakaoSdk
import com.zoyi.channel.plugin.android.ChannelIO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import pet.perpet.data.ApiConfigProvider
import pet.perpet.data.ApiConfigProviderFactory
import pet.perpet.data.DataConfig
import pet.perpet.equal.presentation.appDataClear
import pet.perpet.equal.presentation.base.bus.RxBus2
import pet.perpet.equal.presentation.createStartActivityIntent
import pet.perpet.equal.presentation.getLogout
import pet.perpet.equal.support.AppAuthInterceptor
import pet.perpet.equal.support.deeplink.DeepLink
import pet.perpet.equal.support.logger.AppLogger


class MyApplication : MultiDexApplication(), ImageLoaderFactory {

    //======================================================================
    // Variables
    //======================================================================

    private lateinit var lifecycleCallbacks: MyActivityLifecycleCallbacks

    private val applicationScope by lazy {
        CoroutineScope(Dispatchers.Main)
    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreate() {
        super.onCreate()
        application = this
        lifecycleCallbacks = object : MyActivityLifecycleCallbacks() {
            override fun onDestroy() {
                // Nothing
            }
        }

        registerActivityLifecycleCallbacks(lifecycleCallbacks)
        ChannelIO.initialize(this)
        DeepLink.init()
        initKakao()
        initImageLibrary()
        DataConfig.init(this, object : ApiConfigProviderFactory() {
            override fun create(): ApiConfigProvider {
                return object : ApiConfigProvider() {

                    override val userAgent: String
                        get() = "Android"

                    override val application: Context
                        get() = this@MyApplication

                    override fun createInterceptor(): Interceptor {
                        return AppAuthInterceptor()
                    }

                    override val accessToken: String?
                        get() = ""

                    override val refreshToken: String?
                        get() = ""

                    override val logger: Boolean
                        get() = AppConfig.LOG_ENABLE

                    override val clintId: String
                        get() = ""

                    override val domain: String
                        get() = "https://api.equal.pet/"

                    override fun logout() {
                        applicationScope.launch {
                            getLogout {
                                appDataClear()
                                val intent = createStartActivityIntent(this@MyApplication)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                this@MyApplication.startActivity(intent)
                                this@MyApplication.finishAll()
                            }
                        }
                    }
                }
            }
        })
//        DeepLink.fetchDeferredAppLinkData(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    //======================================================================
    // Public Methods
    //======================================================================

    fun findActivity(clazz: Class<*>): Activity? {
        activities.forEach {
            AppLogger.w(
                AppLogger.Tag.ACTIVITY_LIFE_CYCLE,
                "findActivity > ${it::class.java.name}, target : ${clazz::class}"
            )
            if (it::class.java.name == clazz.name) {
                return it
            }
        }
        return null
    }

    fun finishAll(): Activity? {
        activities.forEach {
            AppLogger.w(
                AppLogger.Tag.ACTIVITY_LIFE_CYCLE,
                "finishAll > ${it::class.java.name}"
            )
            it.finish()
        }
        return null
    }

    fun release() {
        finishAll()
        unregisterActivityLifecycleCallbacks(lifecycleCallbacks)
        RxBus2.clear()
        try {
            applicationScope.cancel()
        } catch (e: java.lang.Exception) {
            AppLogger.printStackTrace(e)
        }
    }

    //======================================================================
    // Private Methods
    //======================================================================

    private fun initImageLibrary() {
        val imageLoader = ImageLoader.Builder(applicationContext)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()
        Coil.setImageLoader(imageLoader)
        Fresco.initialize(this)
    }

    private fun initKakao() {
        KakaoSdk.init(this, getString(R.string.kakao_native_app_key))
    }

    companion object {
        private val activities = ArrayList<Activity>()

        var application: MyApplication? = null
        var contentDetail: Boolean? = false
        var mainOpen: Boolean? = false



        internal fun isExculde(act: Activity): Boolean {
            /* execludeActivity.forEach {
                 if (it.name == act::class.java.name) {
                     return true
                 }
             }*/
            return false
        }
    }

    //======================================================================
    // MyActivityLifecycleCallbacks
    //======================================================================

    internal abstract class MyActivityLifecycleCallbacks : ActivityLifecycleCallbacks {
        // running activity count

        abstract fun onDestroy()

        override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
            if (isExculde(activity) == true) {
                return
            }
            activities.add(activity)
            logCycle(activity, "onActivityCreated : bundle : $bundle")
        }

        override fun onActivityStarted(activity: Activity) {
            if (isExculde(activity) == true) {
                return
            }
            logCycle(activity, "onActivityStarted")
        }

        override fun onActivityResumed(activity: Activity) {
            if (isExculde(activity) == true) {
                return
            }
            logCycle(activity, "onActivityResumed")
        }

        override fun onActivityPaused(activity: Activity) {
            if (isExculde(activity) == true) {
                return
            }
            logCycle(activity, "onActivityPaused")
        }

        override fun onActivityStopped(activity: Activity) {
            if (isExculde(activity) == true) {
                return
            }
            logCycle(activity, "onActivityStopped")
        }

        override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {
            if (isExculde(activity) == true) {
                return
            }
            logCycle(activity, "onActivitySaveInstanceState : bundle : $bundle")
        }

        override fun onActivityDestroyed(activity: Activity) {
            if (isExculde(activity) == true) {
                return
            }
            activities.remove(activity)
            logCycle(activity, "onActivityDestroyed size : " + activities.size)
            if (activities.isEmpty() == true) {
                activities.clear()
                onDestroy()
            }
        }

        private fun logCycle(activity: Activity, message: String) {
            AppLogger.d(
                AppLogger.Tag.ACTIVITY_LIFE_CYCLE,
                String.format(
                    "[%s]: size : ${activities.size} %s",
                    activity.javaClass.simpleName,
                    message
                )
            )
        }
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .build()
    }
}
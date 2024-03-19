package pet.perpet.equal.presentation.base.bus


import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import pet.perpet.equal.support.logger.AppLogger
import pet.perpet.equal.support.logger.w




@Suppress("UnstableApiUsage")
object RxBus2 {
//======================================================================
    // Variables
    //======================================================================

    private val bus = InnerPublishSubject<Any>()

    private val busMap = HashMap<String, InnerPublishSubject<Any>>()

    //======================================================================
    // Public Methods
    //======================================================================

    fun <T> subscribe(fragment: Fragment, type: Class<T>, event: (value: T) -> Unit) {
        bus.subscribeInternal(fragment.lifecycle, type, event)
    }

    fun <T> subscribe(fragment: FragmentActivity, type: Class<T>, event: (value: T) -> Unit) {
        bus.subscribeInternal(fragment.lifecycle, type, event)
    }

    fun <T> subscribe(lifecycle: Lifecycle, type: Class<T>, event: (value: T) -> Unit) {
        bus.subscribeInternal(lifecycle, type, event)
    }

    fun post(event: Any) {
        bus.post(event)
    }

    fun of(key: String): InnerPublishSubject<Any>? {
        fun get(key: String): InnerPublishSubject<Any>? {

            return busMap[key].run {
                AppLogger.Tag.PRESENTATION.w("[${this@RxBus2::class.java.simpleName}] of[${key}] : ${this}")
                this
            }
        }
        try {
            val bus = get(key)
            if (bus == null) {
                val obj = InnerPublishSubject<Any>()
                AppLogger.Tag.PRESENTATION.w("[${this::class.java.simpleName}] of : ${obj}")
                busMap[key] = obj
                return obj
            }
            return get(key)
        } catch (e: Exception) {
            AppLogger.printStackTrace(e)
        }
        AppLogger.Tag.PRESENTATION.w("[${this::class.java.simpleName}] of null")
        return null
    }

    fun clear() {
        try {
            busMap.clear()
        } catch (e: java.lang.Exception) {
            AppLogger.printStackTrace(e)
        }
    }
}

class InnerPublishSubject<T> {
    private val bus = PublishSubject.create<T>()

    //======================================================================
    // Public Methods
    //======================================================================

    fun <T> subscribe(fragment: Fragment, type: Class<T>, event: (value: T) -> Unit) {
        subscribeInternal(fragment.lifecycle, type, event)
    }

    fun subscribe(fragment: Fragment, event: (value: Any?) -> Unit) {
        subscribeInternal(fragment.lifecycle, event)
    }

    fun <T> subscribe(fragment: FragmentActivity, type: Class<T>, event: (value: T) -> Unit) {
        subscribeInternal(fragment.lifecycle, type, event)
    }

    fun subscribe(fragment: FragmentActivity, event: (value: Any?) -> Unit) {
        subscribeInternal(fragment.lifecycle, event)
    }

    fun post(event: T) {
        AppLogger.Tag.PRESENTATION.w("[${this::class.java.simpleName}] post : ${event}")
        bus.onNext(event)
    }

    //======================================================================
    // Local Methods
    //======================================================================

    @SuppressLint("CheckResult")
    internal fun <T> subscribeInternal(
        lifecycle: Lifecycle,
        type: Class<T>,
        event: (value: T) -> Unit,
    ) {
        bus.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .ofType(type)
            .doOnError { error -> }
            .subscribe { item ->
                event(item)
            }
    }

    @SuppressLint("CheckResult")
    private fun subscribeInternal(lifecycle: Lifecycle, event: (value: Any?) -> Unit) {
        bus.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { error -> }
            .subscribe { item ->
                AppLogger.Tag.PRESENTATION.w("[${this::class.java.simpleName}] subscribeInternal] : ${item}")
                event(item)
            }
    }
}

class RxLiveData<T> {
    private var bus: MutableLiveData<T>? = MutableLiveData()

    private var lifeCycle: Lifecycle? = null

    fun observe(fragment: Fragment, event: (value: T?) -> Unit): RxLiveData<T> {
        if (bus == null) {
            bus = MutableLiveData()
        }
        if (lifeCycle == null) {
            lifeCycle = fragment.lifecycle
            lifeCycle?.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestory() {
                    AppLogger.w(AppLogger.Tag.ACTIVITY_LIFE_CYCLE, "ChannelLiveData onDestory")
                    lifeCycle?.removeObserver(this)
                    lifeCycle = null
                }
            })
        }
        bus?.observe(fragment, Observer {
            event(it)
        })
        return this
    }

    fun observe(owner: FragmentActivity, event: (value: T?) -> Unit): RxLiveData<T> {
        if (bus == null) {
            bus = MutableLiveData()
        }
        if (lifeCycle == null) {
            lifeCycle = owner.lifecycle
            lifeCycle?.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
                fun onDestory() {
                    AppLogger.w(AppLogger.Tag.ACTIVITY_LIFE_CYCLE, "ChannelLiveData onDestory")
                    lifeCycle?.removeObserver(this)
                    lifeCycle = null
                }
            })
        }
        bus?.observe(owner, Observer {
            event(it)
        })
        return this
    }

    fun postValue(event: T?) {
        if (bus == null) {
            bus = MutableLiveData()
        }
        bus?.postValue(event)
    }
}


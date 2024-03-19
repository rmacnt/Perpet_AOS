package pet.perpet.equal.presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.Spanned
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.widget.Checkable
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import com.facebook.binaryresource.FileBinaryResource
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory
import com.facebook.imagepipeline.core.ImagePipelineFactory
import com.facebook.imagepipeline.request.ImageRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pet.perpet.data.nonnull
import pet.perpet.domain.TokenStore
import pet.perpet.domain.UserStore
import pet.perpet.equal.MyApplication
import pet.perpet.equal.R
import pet.perpet.equal.presentation.intakecheck.IntakeStore
import pet.perpet.equal.presentation.intakecheck.model.IntakeAlarm
import pet.perpet.equal.support.alarm.AlarmReceiver
import pet.perpet.equal.support.logger.AppLogger
import pet.perpet.framework.activity.AppCompatActivity
import pet.perpet.framework.channel.ChannelProvider
import pet.perpet.framework.channel.ChannelProviders
import pet.perpet.framework.fragment.DialogFragmentNavigation
import pet.perpet.framework.fragment.DialogFragmentSupport
import pet.perpet.framework.fragment.UseViewModel
import pet.perpet.framework.util.PermissionChecker
import pet.perpet.framework.util.http.HtmlFactory
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewAdapter
import pet.perpet.framework.widget.recyclerview.BaseRecyclerViewHolder
import pet.perpet.framework.widget.recyclerview.OnViewHolderEventListener
import java.io.File
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.coroutines.CoroutineContext


fun UseViewModel.safetyCallback(callback: () -> Unit) {
    fragment?.let {
        MutableLiveData<Boolean>().run {
            this.observe(it, androidx.lifecycle.Observer {
                callback()
            })
            this.postValue(true)
        }
    }
}


inline fun <T> Boolean.takeTrue(callback: () -> T): T? {
    return if (this) {
        callback()
    } else {
        null
    }
}

inline fun <T> Boolean.takeFalse(callback: () -> T): T? {
    return if (!this) {
        callback()
    } else {
        null
    }
}

fun String?.toIntCatch(): Int {
    try {
        return this.toString().toInt()
    } catch (e: Exception) {
    }
    return 0
}

fun <T> Any.asObject(): T? {
    try {
        return this as T
    } catch (e: Exception) {

    }
    return null
}

fun Date.toYYYmmdd(split: String = "."): String {
    return SimpleDateFormat("yyyy${split}MM${split}dd").format(this.time)
}

@SuppressLint("SimpleDateFormat")
fun Date.toYYYmm(split: String = "."): String {
    return SimpleDateFormat("yyyy${split}MM").format(this.time)
}


//======================================================================
// List
//======================================================================

inline fun <T> List<T>.join(delimiter: String): String {
    return TextUtils.join(delimiter, this)
}


fun List<Checkable>?.singleChecked(item: Checkable): Boolean {
    if (this?.size.nonnull() <= 0) {
        return false
    }
    var updateCount = 0
    this?.forEach {
        if (item != it) {
            if (it.isChecked) {
                it.isChecked = false
                updateCount += 1
            }
        }
    }
    return updateCount > 0
}

fun List<Checkable>?.isLimitChecked(limit: Int = 1): Boolean {
    var checkedCount = 0
    this?.forEachIndexed { index, checkable ->
        if (checkedCount >= limit) {
            return true
        }
        if (checkable.isChecked) {
            checkedCount += 1
        }
    }
    return checkedCount >= limit
}

fun Fragment?.dismiss() {
    if (this is DialogFragmentSupport<*>) {
        this.dismiss(Bundle())
    }
}

fun Fragment?.dismiss(bundle: Bundle) {
    if (this is DialogFragmentSupport<*>) {
        this.dismiss(bundle)
    }
}

@DimenRes
fun Int.getDimensionPixelSize(context: Context): Int {
    return context.resources.getDimensionPixelSize(this)
}

@SuppressLint("ResourceType")
@ColorRes
fun Int.getColor(context: Context): Int {
    return ContextCompat.getColor(context, this)
}

fun createIoScope(): CoroutineScope {
    return CoroutineScope(Dispatchers.IO)
}

fun Context.supportFragmentManager(): FragmentManager? {
    if (this is AppCompatActivity) {
        return this.supportFragmentManager
    }
    return null
}

fun createDefaultScope(): CoroutineScope {
    return CoroutineScope(Dispatchers.Default)
}

fun createMainScope(): CoroutineScope {
    return CoroutineScope(Dispatchers.Main)
}

fun createScope(context: CoroutineContext): CoroutineScope {
    return CoroutineScope(context)
}

fun Int.toDecimalFormat(): String {
    val decimalFormat = DecimalFormat()
    return decimalFormat.format(this.toLong())
}

fun Float.toDecimalFormat(): String {
    val decimalFormat = DecimalFormat()
    return decimalFormat.format(this)
}


fun Context.getLogout(callback: () -> Unit) = AlertDialog.Builder(this)
    .setMessage(getString(R.string.app_message_logout))
    .setCancelable(false)
    .setPositiveButton("시작하기") { _, i ->
        callback()
    }
    .create()
    .safetyShow()

////======================================================================
//// Permission
////======================================================================

fun Context?.asAppCompatActivity(): AppCompatActivity? {
    return if (this is AppCompatActivity) {
        this
    } else {
        null
    }
}

fun Activity?.asAppCompatActivity(): AppCompatActivity? {
    return if (this is AppCompatActivity) {
        this
    } else {
        null
    }
}

fun BaseRecyclerViewAdapter.applySubItemHolderEvent(src: BaseRecyclerViewHolder<*>) {
    this.run {
        this.setHolderEventListener(object : OnViewHolderEventListener {
            override fun onRecyclerViewHolderEvent(
                holder: BaseRecyclerViewHolder<*>,
                id: Int,
                bundle: Bundle,
            ) {
                src.dispatchOnViewHolderEvent(holder, id, bundle)
            }
        })
    }
}

fun Context?.checkPermissionCall(
    permission: ArrayList<String>,
    callback: (
        grants: BooleanArray?,
        showRequestPermissions: BooleanArray?,
    ) -> Unit,
) {
    this?.asAppCompatActivity()?.run {
        permissionChecker.makeRequestPermissions(
            permission,
            object : PermissionChecker.OnSupportRequestPermissionsResultCallback() {
                override fun onResult(
                    grants: BooleanArray?,
                    showRequestPermissions: BooleanArray?,
                ) {
                    callback(grants, showRequestPermissions)
                }
            })
    }
}

fun Dialog.safetyShow() {
    try {
        this.show()
    } catch (e: java.lang.Exception) {
        AppLogger.printStackTrace(e)
    }
}

fun <R> Fragment?.dispatchDismissToResult(r: R?) {
    DialogFragmentNavigation.dispatchDismissToResult(this, r)
}

fun <R> Fragment?.dispatchDismissToResult(r: R?, dismiss: Boolean = true) {
    DialogFragmentNavigation.dispatchDismissToResult(this, r, dismiss)
}

fun getScreenHeight(context: Context?): Int {
    return if (context == null) {
        0
    } else getDisplayMetrics(context).heightPixels
}

fun getDisplayMetrics(context: Context): DisplayMetrics {
    val displayMetrics = DisplayMetrics()
    (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        .getMetrics(displayMetrics)
    return displayMetrics
}


fun pxToDp(context: Context, px: Int): Float {
    var dp = 0.0f
    try {
        val metrics = context.resources.displayMetrics
        dp = px / (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    } catch (e: java.lang.Exception) {

    }
    return dp
}

fun dpToPx(context: Context, dp: Float?): Int {
    var px = 0.0f
    try {
        px = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp!!,
            context.resources.displayMetrics
        )
    } catch (ex: java.lang.Exception) {
    }
    return px.toInt()
}


fun Dialog.safetyDismiss() {
    try {
        this.dismiss()
    } catch (e: java.lang.Exception) {
        AppLogger.printStackTrace(e)
    }
}


fun UseViewModel.observeBindingNotify(call: (value: Boolean) -> Unit) {
    this.fragment?.let {
        this.bindingNotify.observe(it, androidx.lifecycle.Observer { result ->
            call(result.nonnull())
        })
    }
}


fun UseViewModel.observeBindingNotifyView(call: (value: View) -> Unit) {
    this.fragment?.let {
        this.bindingNotifyView.observe(it, androidx.lifecycle.Observer { result ->
            call(result)
        })
    }
}


fun UseViewModel.observeBindingNotifyNew(call: (value: Boolean) -> Unit) {
    this.fragment?.let {
        this.bindingNotifyNew.observe(it, androidx.lifecycle.Observer { result ->
            call(result.nonnull())
        })
    }
}

fun getCachePath(path: String): File? {
    val imageRequest = ImageRequest.fromUri(path)
    val cacheKey = DefaultCacheKeyFactory.getInstance()
        .getEncodedCacheKey(imageRequest, null)
    val resource = ImagePipelineFactory.getInstance().mainFileCache.getResource(cacheKey)
    return resource?.let {
        if (it is FileBinaryResource) {
            it.file
        } else {
            null
        }
    }
}


private fun Activity.openAppSetExactAlarmPermissionSettings() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val alarmManager = ContextCompat.getSystemService(this, AlarmManager::class.java)
        if (alarmManager?.canScheduleExactAlarms() == false) {
            Intent().also { intent ->
                intent.action = Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM
                startActivity(intent)
            }
        }
    }
}

fun getEmptyDraw(empty: Boolean): Drawable {
    val color = if (empty) Color.parseColor("#efefef") else Color.TRANSPARENT
    val draw = ColorDrawable(color)
    return draw
}

val Fragment.activityChannelProvider: ChannelProvider?
    get() = ChannelProviders.of(requireActivity())


fun logout(callback: () -> Unit) {
    fun call() {
        createMainScope().launch {
            callback()
        }
    }
    Thread(Runnable {
        try {
            appDataClear()
            call()
        } catch (e: java.lang.Exception) {
            AppLogger.printStackTrace(e)
            call()
        }
    }).start()
}

fun appDataClear() {

    TokenStore.clear()
    UserStore.clear()

    val intent = Intent(getContext(), AlarmReceiver::class.java)
    val pendingIntent = PendingIntent.getBroadcast(getContext(), 7727, intent, IntakeStore.ALARM_FLAG)

    val am = getContext()?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    if (pendingIntent != null) {
        am.cancel(pendingIntent)
    }
}

fun AppCompatActivity.checkLogin() {
    if (TokenStore.isUseToken == false) {
        getLogout {
            goStart(this@checkLogin)
            finish()
        }
    }
}

////======================================================================
//// Context
////======================================================================

fun getString(resId: Int): String? {
    return MyApplication.application?.getString(resId)
}

fun getContext(): Context? {
    return MyApplication.application?.applicationContext
}

fun fromHtml(
    @StringRes formatRes: Int,
    vararg objects: Any?,
): Spanned? {
    return MyApplication.application?.let { HtmlFactory.fromHtml(it, formatRes, *objects) }
}

fun getStringArray(resId: Int): Array<String>? {
    return MyApplication.application?.resources?.getStringArray(resId)
}

fun getString(resId: Int, vararg formatArgs: Any?): String? {
    return MyApplication.application?.getString(resId, formatArgs)
}

fun getDrawable(resId: Int): Drawable? {
    return MyApplication.application?.let { ContextCompat.getDrawable(it, resId) }
}

fun getDimen(resId: Int): Float? {
    return MyApplication.application?.resources?.getDimension(resId)
}

fun getColor(@ColorRes res: Int): Int? {
    return MyApplication.application?.applicationContext?.let {
        ContextCompat.getColor(
            it,
            res
        )
    }
}
package pet.perpet.equal.presentation.base.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import pet.perpet.equal.R
import java.lang.Exception

abstract class BottomSheetDelegate {

    private var behavior: BottomSheetBehavior<FrameLayout>? = null

    private var container: FrameLayout? = null

    private var dismissWithAnimation = false

    private var cancelable: Boolean = true

    private var canceledOnTouchOutside = true

    private var canceledOnTouchOutsideSet = false

    var callback: BottomSheetBehavior.BottomSheetCallback? = null

    abstract val fragment: Fragment

    fun onCreate() {
        val window: Window? = fragment.activity?.window
        if (window != null) {
            val mode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
            window.setSoftInputMode(mode)
            /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            }
            window.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )*/
        }
    }

    fun setState(state: Int) {
        try {
            if (this.currentState() != state) {
                behavior?.state = state
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setCancelable(cancelable: Boolean) {
        if (this.cancelable != cancelable) {
            this.cancelable = cancelable
            behavior?.isHideable = cancelable
        }
    }

    fun onStart() {
        if (behavior != null && behavior?.state == BottomSheetBehavior.STATE_HIDDEN) {
            behavior?.state = BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    fun cancel() {
        if (!dismissWithAnimation || behavior?.state == BottomSheetBehavior.STATE_HIDDEN) {
            behavior?.setState(BottomSheetBehavior.STATE_HIDDEN)
        } else {
            behavior?.setState(BottomSheetBehavior.STATE_HIDDEN)
        }
    }

    fun setCanceledOnTouchOutside(cancel: Boolean) {
        if (cancel && !cancelable) {
            cancelable = true
        }
        canceledOnTouchOutside = cancel
        canceledOnTouchOutsideSet = true
    }

    private fun ensureContainerAndBehavior(view: View) {
        behavior = BottomSheetBehavior.from(view.findViewById(R.id.design_bottom_sheet))
        behavior?.state = BottomSheetBehavior.STATE_HIDDEN
        behavior?.setBottomSheetCallback(bottomSheetCallback)
        behavior?.isHideable = cancelable
    }

    fun stateHidden(): Boolean {
        return if (behavior?.state == BottomSheetBehavior.STATE_EXPANDED || behavior?.state == BottomSheetBehavior.STATE_COLLAPSED) {
            state(BottomSheetBehavior.STATE_HIDDEN)
            true
        } else {
            false
        }
    }

    fun state(state: Int) {
        behavior?.state = state
    }

    fun currentState(): Int? {
        return behavior?.state
    }

    fun peekHeight(height: Int) {
        behavior?.peekHeight = height
    }

    @SuppressLint("ClickableViewAccessibility")
    fun onCreateView(view: View) {
        ensureContainerAndBehavior(view)
        container = view.findViewById(R.id.container)
//        val coordinator = container!!.findViewById<View>(R.id.coordinator) as CoordinatorLayout
        val bottomSheet = container!!.findViewById<View>(R.id.design_bottom_sheet)
        // We treat the CoordinatorLayout as outside the dialog though it is technically inside
        // 요기 누르면 하위뷰에 터치 이벤트가 전달이 안된다.
        /*coordinator
            .findViewById<View>(R.id.touch_outside)
            .setOnClickListener {
                if (cancelable && canceledOnTouchOutsideSet) {
                    cancel()
                }
            }*/
        // Handle accessibility events
        ViewCompat.setAccessibilityDelegate(
            bottomSheet,
            object : AccessibilityDelegateCompat() {
                override fun onInitializeAccessibilityNodeInfo(
                    host: View, info: AccessibilityNodeInfoCompat
                ) {
                    super.onInitializeAccessibilityNodeInfo(host, info)
                    if (cancelable) {
                        info.addAction(AccessibilityNodeInfoCompat.ACTION_DISMISS)
                        info.isDismissable = true
                    } else {
                        info.isDismissable = false
                    }
                }

                override fun performAccessibilityAction(
                    host: View,
                    action: Int,
                    args: Bundle?
                ): Boolean {
                    if (action == AccessibilityNodeInfoCompat.ACTION_DISMISS && cancelable) {
                        cancel()
                        return true
                    }
                    return super.performAccessibilityAction(host, action, args)
                }
            })
    }

    fun onDestroy() {
        behavior?.setBottomSheetCallback(null)
    }

    private val bottomSheetCallback: BottomSheetBehavior.BottomSheetCallback =
        object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(
                bottomSheet: View, @BottomSheetBehavior.State newState: Int
            ) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    cancel()
                }
                callback?.onStateChanged(bottomSheet, newState)
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                callback?.onSlide(bottomSheet, slideOffset)
            }
        }
}
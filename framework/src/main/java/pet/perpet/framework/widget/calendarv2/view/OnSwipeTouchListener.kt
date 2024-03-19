package pet.perpet.framework.widget.calendarv2.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import kotlin.math.abs

abstract class OnSwipeTouchListener(ctx: Context) : OnTouchListener {
    init {
        gestureDetector = GestureDetector(ctx, GestureListener())
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        return try {
            gestureDetector!!.onTouchEvent(event)
        }catch (e: Exception) {
            false
        }

    }


    private inner class GestureListener : SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent): Boolean {
            return false
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            var result = false
            try {
                val diffY = e2.y - e1.y
                if (abs(diffY) > SWIPE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom()
                    } else {
                        onSwipeTop()
                    }
                    result = false
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }

            return result
        }

    }

    companion object {
        var gestureDetector: GestureDetector? = null
        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHOLD = 100
    }


    abstract fun onSwipeTop()

    abstract fun onSwipeBottom()
}
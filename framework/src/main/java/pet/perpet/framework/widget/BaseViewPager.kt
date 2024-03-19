package pet.perpet.framework.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

open class BaseViewPager(context: Context, attrs: AttributeSet?) : ViewPager(context, attrs) {

    //======================================================================
    // Variables
    //======================================================================

    var swiping = true

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onTouchEvent(ev: MotionEvent): Boolean {
        if (swiping == false) {
            return false
        }
        try {
            return super.onTouchEvent(ev)
        } catch (ignored: Exception) {
        }

        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (swiping == false) {
            return false
        }
        try {
            return super.onInterceptTouchEvent(ev)
        } catch (ignored: Exception) {
        }

        return false
    }
}
package pet.perpet.framework.fragment

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator
import android.widget.Scroller
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import java.lang.reflect.Field

/**
 * A [ViewPager] that allows pseudo-infinite paging with a wrap-around effect. Should be used with an [ ].
 */
class InfinityViewPager : ViewPager {
    private var isPagingEnabled = true

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(
        context, attrs
    ) {
        init()
    }

    private var mScroller: FixedSpeedScroller? = null
    override fun setAdapter(adapter: PagerAdapter?) {
        super.setAdapter(adapter)
        // offset first element so that we can scroll to the left
        currentItem = 0
    }

    override fun setCurrentItem(item: Int) {
        // offset the current item to ensure there is space to scroll
        setCurrentItem(item, false)
    }

    override fun setCurrentItem(item: Int, smoothScroll: Boolean) {
        var item = item
        val adapter = adapter ?: return
        if (adapter.count == 0) {
            super.setCurrentItem(item, smoothScroll)
            return
        }
        super.setCurrentItem(item, smoothScroll)
    }

    fun setPosition(position: Int) {
        var movePosition = position - currentItem
        var setPosition = super.getCurrentItem() + movePosition
        super.setCurrentItem(setPosition, true)
    }

    override fun getCurrentItem(): Int {
        val adapter = adapter ?: return super.getCurrentItem()
        if (adapter.count == 0) {
            return super.getCurrentItem()
        }
        val position = super.getCurrentItem()
        return if (adapter is InfinityPagerAdapter) {
            // Return the actual item position in the data backing InfinitePagerAdapter
            position
        } else {
            super.getCurrentItem()
        }
    }

    private val offsetAmount: Int
        private get() {
            if (adapter?.count == 0) {
                return 0
            }
            return if (adapter is InfinityPagerAdapter) {
                val infAdapter = adapter as InfinityPagerAdapter

                infAdapter.realCount
            } else {
                0
            }
        }

    fun setupCurrentItem(cur: Int) {
        try {
            val viewpager: Class<*> = ViewPager::class.java
            val mCurItemField: Field = viewpager.getDeclaredField("mCurItem")
            mCurItemField.isAccessible = true
            mCurItemField.set(this, cur)
        } catch (ignored: java.lang.Exception) {
        }
    }

    // 페이저가 자식 아이템 height 만큼 늘어 나게끔 설정.
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var heightMeasureSpec = heightMeasureSpec

        val mode = MeasureSpec.getMode(heightMeasureSpec)

        if (mode == MeasureSpec.UNSPECIFIED || mode == MeasureSpec.AT_MOST) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
            var height = 0
            for (i in 0 until childCount) {
                val child = getChildAt(i)
                child.measure(
                    widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
                )
                val childHeight = child.measuredHeight
                if (childHeight > height) height = childHeight
            }

            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        try {
            if (isHorizontalScrollDisallow) {
                return false
            }
        } catch (e: Exception) {
            Log.e("Exception : ", e.message ?: "")
        }
        return isPagingEnabled && super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (isHorizontalScrollDisallow) {
            false
        } else isPagingEnabled && super.onInterceptTouchEvent(event)
    }

    fun setPagingEnabled(enable: Boolean) {
        isPagingEnabled = enable
    }

    override fun canScrollHorizontally(direction: Int): Boolean {
        //Disable horizontal scrolling
        return if (isPagingEnabled) {
            super.canScrollHorizontally(direction)
        } else {
            false
        }
    }

    private fun init() {
        try {
            val viewpager: Class<*> = ViewPager::class.java
            val scroller = viewpager.getDeclaredField("mScroller")
            scroller.isAccessible = true
            mScroller = FixedSpeedScroller(
                context,
                DecelerateInterpolator()
            )
            scroller[this] = mScroller
        } catch (ignored: Exception) {
        }
    }

    private inner class FixedSpeedScroller : Scroller {
        private var mDuration = 500

        constructor(context: Context?) : super(context) {}
        constructor(context: Context?, interpolator: Interpolator?) : super(
            context,
            interpolator
        ) {
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        constructor(context: Context?, interpolator: Interpolator?, flywheel: Boolean) : super(
            context,
            interpolator,
            flywheel
        ) {
        }

        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration)
        }

        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int) {
            // Ignore received duration, use fixed one instead
            super.startScroll(startX, startY, dx, dy, mDuration)
        }

        fun setScrollDuration(duration: Int) {
            mDuration = duration
        }
    }

    companion object {
        var isHorizontalScrollDisallow = false
    }
}
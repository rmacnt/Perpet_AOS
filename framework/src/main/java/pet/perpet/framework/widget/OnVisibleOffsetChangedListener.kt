package pet.perpet.framework.widget

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

abstract class OnVisibleOffsetChangedListener : AppBarLayout.OnOffsetChangedListener {

    //======================================================================
    // Variables
    //======================================================================

    private var preVerticalOffset: Int = 0

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onOffsetChanged(layout: AppBarLayout, verticalOffset: Int) {
        val maxScroll = layout.totalScrollRange
        val percentage = abs(verticalOffset).toFloat() / maxScroll.toFloat()
        handleViewVisibility(percentage)
        preVerticalOffset = verticalOffset
    }

    //======================================================================
    // Abstract Methods
    //======================================================================

    abstract fun handleViewVisibility(percentage: Float)
}
package pet.perpet.framework.widget.recyclerview.decoration

import android.graphics.Rect
import android.view.View

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.flexbox.FlexboxLayoutManager

open class SpacingItemDecoration(
    recyclerView: RecyclerView, horizontalFillSpacing: Boolean,

    //======================================================================
    // Variables
    //======================================================================

    private val mVerticalSpacing: Int, horizontalSpacing: Int
) : RecyclerView.ItemDecoration() {

    private var mHorizontalSpacing: Int = 0

    private var mOutsideMargin: Int = 0

    open val isUseOverrideSpacing: Boolean
        get() = false

    //======================================================================
    // Constructor
    //======================================================================

    constructor(recyclerView: RecyclerView, verticalSpacing: Int, horizontalSpacing: Int) : this(
        recyclerView,
        true,
        verticalSpacing,
        horizontalSpacing
    )

    init {
        mHorizontalSpacing = horizontalSpacing

        if (horizontalFillSpacing == true) {
            if (recyclerView.layoutManager is StaggeredGridLayoutManager) {
                val spanCount = (recyclerView.layoutManager as StaggeredGridLayoutManager).spanCount
                if (spanCount <= 1) {
                    mHorizontalSpacing = 0
                }
            }
        }

        if (mHorizontalSpacing > 0) {
            recyclerView.clipToPadding = false

            val top = recyclerView.paddingTop
            val bottom = recyclerView.paddingBottom

            val left = recyclerView.paddingLeft + mHorizontalSpacing / 2
            val right = recyclerView.paddingRight + mHorizontalSpacing / 2

            recyclerView.setPadding(left, top, right, bottom)
        }
    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val layoutManager = parent.layoutManager

        if (layoutManager is StaggeredGridLayoutManager) {
            getStaggeredGridLayoutManagerItemOffsets(outRect, view, parent, state)
        } else if (layoutManager is GridLayoutManager) {
            getGridLayoutManagerItemOffsets(outRect, view, parent, state)
        } else if (layoutManager is LinearLayoutManager) {
            getLinearLayoutManagerItemOffsets(outRect, view, parent, state)
        } else if (layoutManager is FlexboxLayoutManager) {
            getFlexboxLayoutManagerItemOffsets(outRect, view, parent, state)
        }
    }


    open fun getStartPosition(): Int {
        return 0
    }

    //======================================================================
    // Private Methods
    //======================================================================

    private fun getStaggeredGridLayoutManagerItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val manager = parent.layoutManager as StaggeredGridLayoutManager?
        val itemCount = manager!!.itemCount
        val spanCount = manager.spanCount
        val position = manager.getPosition(view)
        val orientation = manager.orientation
        val firstItem = position == getStartPosition()
        val lastItem = position >= itemCount - 1

        if (orientation == StaggeredGridLayoutManager.VERTICAL) {
            if (mVerticalSpacing > 0) {
                outRect.top = mVerticalSpacing
                outRect.bottom = if (lastItem) mVerticalSpacing else mOutsideMargin
            }
            if (mHorizontalSpacing > 0) {
                outRect.left = mHorizontalSpacing / 2
                outRect.right = mHorizontalSpacing / 2
            }
        } else {
            if (mVerticalSpacing > 0) {
                outRect.top = mVerticalSpacing
                outRect.bottom = mVerticalSpacing
            }
            if (mHorizontalSpacing > 0) {
                outRect.left = if (firstItem) mOutsideMargin else mHorizontalSpacing / 2
                outRect.right = if (lastItem) mOutsideMargin else mHorizontalSpacing / 2
            }
        }

        if (isUseOverrideSpacing == true) {
            val top = getTopSpacing(position)
            val bottom = getBottomSpacing(position)
            val start = getStartSpacing(position)
            val end = getEndSpacing(position)

            if (top > 0) {
                outRect.top = top
            }
            if (bottom > 0) {
                outRect.bottom = bottom
            }
            if (start > 0) {
                outRect.left = start
            }else if(start == -1) {
                outRect.left = - (mHorizontalSpacing / 2)
            }
            if (end > 0) {
                outRect.right = end
            }else if(end == -1) {
                outRect.right = - (mHorizontalSpacing / 2)
            }
        }
    }

    private fun getFlexboxLayoutManagerItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val manager = parent.layoutManager as FlexboxLayoutManager?
        val itemCount = manager!!.itemCount
        val position = manager.getPosition(view)
        val lastItem = position >= itemCount - 1

        if (mVerticalSpacing > 0) {
            outRect.top = mVerticalSpacing
            outRect.bottom = if (lastItem) mVerticalSpacing else mOutsideMargin
        }
        if (mHorizontalSpacing > 0) {
            outRect.left = mHorizontalSpacing / 2
            outRect.right = mHorizontalSpacing / 2
        }

        if (isUseOverrideSpacing == true) {
            val top = getTopSpacing(position)
            val bottom = getBottomSpacing(position)
            val start = getStartSpacing(position)
            val end = getEndSpacing(position)

            if (top > 0) {
                outRect.top = top
            }
            if (bottom > 0) {
                outRect.bottom = bottom
            }
            if (start > 0) {
                outRect.left = start
            }
            if (end > 0) {
                outRect.right = end
            }
        }
    }

    private fun getGridLayoutManagerItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val manager = parent.layoutManager as GridLayoutManager?
        val itemCount = manager!!.itemCount
        val position = manager.getPosition(view)
        val spanCount = manager.spanCount
        val orientation = manager.orientation
        val lastItem = position >= itemCount - 1
        val firstItem = position == 0

        if (orientation == GridLayoutManager.VERTICAL) {
            if (mVerticalSpacing > 0) {
                outRect.top = mVerticalSpacing
                outRect.bottom = if (lastItem) mVerticalSpacing else mOutsideMargin
            }
            if (mHorizontalSpacing > 0) {
                outRect.left = mHorizontalSpacing
                if (spanCount > 1 && lastItem == false && position % spanCount == 0) {
                    outRect.right = mOutsideMargin
                } else {
                    outRect.right = mHorizontalSpacing
                }
            }
        } else {
            if (mVerticalSpacing > 0) {
                outRect.top = mVerticalSpacing
                outRect.bottom = mVerticalSpacing
            }
            if (mHorizontalSpacing > 0) {
                outRect.left = if (firstItem) mOutsideMargin else mHorizontalSpacing / 2
                outRect.right = if (lastItem) mOutsideMargin else mHorizontalSpacing / 2
            }
        }
    }

    private fun getLinearLayoutManagerItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val manager = parent.layoutManager as LinearLayoutManager?
        val itemCount = manager!!.itemCount
        val position = manager.getPosition(view)
        val orientation = manager.orientation
        val lastItem = position >= itemCount - 1
        val firstItem = position == getStartPosition()

        if (orientation == LinearLayoutManager.VERTICAL) {
            if (mVerticalSpacing > 0) {
                if (firstItem) {
                    outRect.top = 0
                } else {
                    outRect.top = mVerticalSpacing
                }
                outRect.bottom = 0
            }
            if (mHorizontalSpacing > 0) {
                outRect.left = mHorizontalSpacing
                outRect.right = mHorizontalSpacing
            }
        } else {
            if (mVerticalSpacing > 0) {
                outRect.top = mVerticalSpacing / 2
                outRect.bottom = if (lastItem) mVerticalSpacing / 2 else mOutsideMargin
            }
            if (mHorizontalSpacing > 0) {
                outRect.left = mHorizontalSpacing / 2
                outRect.right = mHorizontalSpacing / 2
            }
        }

        if (isUseOverrideSpacing == true) {
            val top = getTopSpacing(position)
            val bottom = getBottomSpacing(position)
            val start = getStartSpacing(position)
            val end = getEndSpacing(position)

            if (top > 0) {
                outRect.top = top
            }
            if (bottom > 0) {
                outRect.bottom = bottom
            }
            if (start > 0) {
                outRect.left = start
            }
            if (end > 0) {
                outRect.right = end
            }
        }
    }

    open fun getTopSpacing(position: Int): Int {
        return 0
    }

    open fun getBottomSpacing(position: Int): Int {
        return 0
    }

    open fun getStartSpacing(position: Int): Int {
        return 0
    }

    open fun getEndSpacing(position: Int): Int {
        return 0
    }

    fun setOutsideMargin(outsideMargin: Int) {
        mOutsideMargin = outsideMargin
    }

    companion object {

        //======================================================================
        // Constants
        //======================================================================

        val VERTICAL = 1
    }
}

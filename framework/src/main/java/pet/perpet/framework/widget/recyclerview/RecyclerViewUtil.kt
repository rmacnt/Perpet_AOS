package pet.perpet.framework.widget.recyclerview

import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager


object RecyclerViewUtil {

    @JvmStatic
    fun releaseRecyclerView(recyclerView: RecyclerView) {
        try {
           /* for (i in 0 until recyclerView.adapter?.itemCount.nonnull()) {
                recyclerView.findViewHolderForAdapterPosition(i)?.run {
                    if (this is BaseRecyclerViewHolder<*>) {
                        this
                    } else {
                        null
                    }
                }?.run {
                    release()
                }
            }*/
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    @JvmStatic
    fun findLastCompleteItemPosition(manager: RecyclerView.LayoutManager?): Int {
        if (manager == null) {
            return RecyclerView.NO_POSITION
        }

        if (manager is StaggeredGridLayoutManager) {
            val positions = manager.findLastCompletelyVisibleItemPositions(null)
            var position = RecyclerView.NO_POSITION
            for (array in positions) {
                position = Math.max(array, position)
            }
            return position
        }

        return (manager as? LinearLayoutManager)?.findLastCompletelyVisibleItemPosition()
            ?: RecyclerView.NO_POSITION
    }

    @JvmStatic
    fun findLastVisibleItemPosition(manager: RecyclerView.LayoutManager?): Int {
        if (manager == null) {
            return RecyclerView.NO_POSITION
        }

        if (manager is StaggeredGridLayoutManager) {
            val positions = manager.findLastVisibleItemPositions(null)
            var position = RecyclerView.NO_POSITION
            for (array in positions) {
                position = Math.max(array, position)
            }
            return position
        }

        return (manager as? LinearLayoutManager)?.findLastVisibleItemPosition()
            ?: RecyclerView.NO_POSITION
    }

    @JvmStatic
    fun findFirstVisibleItemPosition(manager: RecyclerView.LayoutManager?): Int {
        if (manager == null) {
            return RecyclerView.NO_POSITION
        }

        if (manager is StaggeredGridLayoutManager) {
            val positions = manager.findFirstVisibleItemPositions(null)
            if (positions != null) {
                var position = Integer.MAX_VALUE
                for (array in positions) {
                    position = Math.min(array, position)
                }
                if (position != Integer.MAX_VALUE) {
                    return position
                }
            }
        }

        return (manager as? LinearLayoutManager)?.findFirstVisibleItemPosition()
            ?: RecyclerView.NO_POSITION
    }

    @JvmStatic
    fun smoothHorizontalTouch(view: RecyclerView) {
        view.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                when (e.action) {
                    MotionEvent.ACTION_MOVE -> {
                        val extent = rv.computeHorizontalScrollExtent()
                        val offset = rv.computeHorizontalScrollOffset()
                        val range = rv.computeHorizontalScrollRange()
                        if (extent + offset >= range) {
                            rv.stopScroll()
                        }
                    }
                }
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {

            }
        })
    }
}

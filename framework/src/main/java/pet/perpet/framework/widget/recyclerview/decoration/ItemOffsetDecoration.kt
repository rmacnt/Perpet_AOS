package pet.perpet.framework.widget.recyclerview.decoration

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.RecyclerView
import pet.perpet.framework.nonnull

open class ItemOffsetDecoration (
    private val paddingLeft: Int,
    private val paddingRight: Int,
    private val paddingTop: Int,
    private var divider: Drawable?
) : RecyclerView.ItemDecoration() {


    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft + paddingLeft
        val right = parent.width - parent.paddingRight - paddingRight
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin + paddingTop
            val bottom = top + (divider?.intrinsicHeight ?: 0)
            if (isShowLine(i) && isChildViewLine(
                    parent.layoutManager?.getPosition(child).nonnull()
                )
            ) {
                divider?.let {
                    it.setBounds(left, top, right, bottom)
                    it.draw(c)
                }
            }
        }
    }

    open fun isShowLine(position: Int): Boolean {
        return true
    }

    open fun isChildViewLine(position: Int): Boolean {
        return true
    }
}
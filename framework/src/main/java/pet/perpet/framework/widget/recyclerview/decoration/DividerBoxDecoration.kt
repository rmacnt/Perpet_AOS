package pet.perpet.framework.widget.recyclerview.decoration

import android.graphics.Canvas
import android.graphics.Paint
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView

class DividerBoxDecoration(
    private val height: Float,
    @ColorInt
    private val color: Int
) : RecyclerView.ItemDecoration() {

    private val paint = Paint()

    init {
        paint.color = color
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingStart.toFloat()
        val right = (parent.width - parent.paddingEnd).toFloat()

        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.top.toFloat()
            val start = 0.toFloat()
            val startBottom = (child.bottom + params.bottomMargin).toFloat()
            val bottom = top + height
            val bottomEnd = child.bottom.toFloat()
            val horizontalMarkPosition = right / 2
            val verticalMarkPosition = height / 2

            if(i < 2)
                c.drawRect (left, top - height, right, bottom, paint)

            c.drawRect (horizontalMarkPosition - verticalMarkPosition, start, horizontalMarkPosition + verticalMarkPosition, startBottom, paint)
            c.drawRect (left, bottomEnd - height , right, bottomEnd , paint)
        }
    }

}
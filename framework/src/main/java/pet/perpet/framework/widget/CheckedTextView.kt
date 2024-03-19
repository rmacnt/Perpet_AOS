package pet.perpet.framework.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Checkable
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter

class CheckedTextView : AppCompatTextView, Checkable {

    //======================================================================
    // Variables
    //======================================================================

    var check: Boolean = false

    //======================================================================
    // Constructor
    //======================================================================

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        if (isChecked) {
            mergeDrawableStates(drawableState, CHECKED_STATE)
        }
        return drawableState
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        invalidate()
    }

    override fun isChecked(): Boolean {
        return check
    }

    override fun toggle() {
        isChecked = !check
    }

    override fun setChecked(checked: Boolean) {
        check = checked
        refreshDrawableState()
    }

    override fun setOnClickListener(onClickListener: OnClickListener?) {
        super.setOnClickListener(OnClickListenerWarp(onClickListener))
    }

    //======================================================================
    // companion
    //======================================================================

    companion object {
        val CHECKED_STATE = intArrayOf(android.R.attr.state_checked)

        @JvmStatic
        @BindingAdapter("setChecked")
        fun setChecked(view : View, checked: Boolean){
            if (view is Checkable){
                view.isChecked = checked
            }
        }
    }

    //======================================================================
    // OnClickListenerWarp
    //======================================================================

    internal inner class OnClickListenerWarp(private val listener: OnClickListener?) : OnClickListener {

        override fun onClick(view: View) {
            toggle()
            listener?.onClick(view)
        }
    }
}
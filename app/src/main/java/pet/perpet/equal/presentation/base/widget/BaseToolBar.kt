package pet.perpet.equal.presentation.base.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.view.ContextThemeWrapper
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter
import pet.perpet.equal.R

class BaseToolBar : Toolbar {

    //======================================================================
    // Constructor
    //======================================================================

    constructor(context: Context) : super(asThemeWrapper(context))

    constructor(context: Context, attrs: AttributeSet?) : super(asThemeWrapper(context), attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        asThemeWrapper(context), attrs, defStyleAttr
    )


    fun setStatusBarMargin(margin: Boolean) {
        if (margin) {
            if (layoutParams is MarginLayoutParams) {
                (layoutParams as MarginLayoutParams).topMargin = getStatusBarHeight(context)
            }
        }
    }

    fun setStatusBarPadding(margin: Boolean) {
        if (margin) {
            clipToPadding = false
            setPadding(0, getStatusBarHeight(context), 0, 0)
        }
    }

    private fun getStatusBarHeight(context: Context): Int {
        val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            return context.resources.getDimensionPixelSize(resourceId)
        }
        return 0
    }

    //======================================================================
    // companion
    //======================================================================

    companion object {

        @JvmStatic
        @BindingAdapter("setNavigationOnClickListener")
        fun onNavigationClicked(toolbar: Toolbar, clickListener: OnClickListener) {
            toolbar.setNavigationOnClickListener(clickListener)
        }

        @JvmStatic
        @BindingAdapter("setStatusBarMargin")
        fun setStatusBarMargin(toolbar: BaseToolBar, margin: Boolean) {
            toolbar.setStatusBarMargin(margin)
        }

        @JvmStatic
        @BindingAdapter("setStatusBarPadding")
        fun setStatusBarPadding(toolbar: BaseToolBar, margin: Boolean) {
            toolbar.setStatusBarPadding(margin)
        }

        @JvmStatic
        @SuppressLint("RestrictedApi")
        fun asThemeWrapper(context: Context): Context {
            return ContextThemeWrapper(context, R.style.AppToolbarThemeWrapper)
        }
    }
}
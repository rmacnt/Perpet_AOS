package pet.perpet.framework.widget

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View

import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter

class BaseEditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.editTextStyle
) : AppCompatEditText(context, attrs, defStyleAttr) {

    //======================================================================
    // Variables
    //======================================================================

    private var backKeyListener: OnBackKeyListener? = null

    private var onTextChanged: OnTextChangedListener? = null

    private var onTextFocus: OnTextFocusChangeListener? = null

    init {
        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onTextChanged?.let {
                    it.onTextChanged(s, start, before, count)
                }
            }

        })

        setOnFocusChangeListener { view, hasFocus ->
            onTextFocus?.let {
                it.onTextFocus(hasFocus)
            }
        }
    }

    //======================================================================
    // Override Methods
    //======================================================================

    override fun onKeyPreIme(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            clearFocus()
            isCursorVisible = false
            if (backKeyListener != null) {
                backKeyListener?.onBackKey()
            }
        }
        return super.onKeyPreIme(keyCode, event)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            requestFocus()
            isCursorVisible = true
        }
        return super.onTouchEvent(event)
    }

    //======================================================================
    // Public Methods
    //======================================================================

    fun setBackKeyListener(onBackKeyListener: OnBackKeyListener) {
        backKeyListener = onBackKeyListener
    }

    fun setTextChangedListener(value: OnTextChangedListener?) {
        onTextChanged = value
    }

    fun setTextFocusChangeListener(value: OnTextFocusChangeListener?) {
        onTextFocus = value
    }

    //======================================================================
    // OnBackKeyListener
    //======================================================================

    interface OnBackKeyListener {
        fun onBackKey(): Boolean
    }

    interface OnTextChangedListener {
        fun onTextChanged(s: CharSequence?, start: Int?, before: Int?, count: Int?)
    }

    interface OnTextFocusChangeListener {
        fun onTextFocus(hasFocus: Boolean)
    }

    companion object {

        @JvmStatic
        @BindingAdapter("setTextChangedListener")
        fun setTextChangedListener(
            view: BaseEditText,
            value: OnTextChangedListener?
        ) {
            view.setTextChangedListener(value)
        }

        @JvmStatic
        @BindingAdapter("setTextFocusListener")
        fun setTextFocusListener(
            view: BaseEditText,
            value: OnTextFocusChangeListener
        ) {
            view.setTextFocusChangeListener(value)
        }

    }
}

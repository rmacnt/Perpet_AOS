package pet.perpet.equal.support.widget.core

import android.content.Context
import android.util.AttributeSet
import android.view.*
import android.widget.TextView
import androidx.core.content.ContextCompat
import pet.perpet.equal.R

class TextWheelViewHolder(private val textView: TextView) :
    BaseWheelPickerView.ViewHolder<TextWheelPickerView.Item>(textView) {
    override fun onBindData(data: TextWheelPickerView.Item) {
        textView.text = data.text
        textView.isEnabled = data.isEnabled
    }
}

class TextWheelAdapter :
    BaseWheelPickerView.Adapter<TextWheelPickerView.Item, TextWheelViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextWheelViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.wheel_picker_item, parent, false) as TextView
        return TextWheelViewHolder(view)
    }
}

class TextWheelPickerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : BaseWheelPickerView(context, attrs, defStyleAttr) {
    data class Item(val id: String, val text: CharSequence, val isEnabled: Boolean = true)


    init {
        attrs?.let {
            context.theme.obtainStyledAttributes(
                it,
                R.styleable.TextWheelPickerView,
                defStyleAttr,
                0
            )
        }
    }
}

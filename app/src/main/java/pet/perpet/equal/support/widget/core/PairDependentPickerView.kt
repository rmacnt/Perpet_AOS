package pet.perpet.equal.support.widget.core

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pet.perpet.equal.R
import java.lang.ref.WeakReference

typealias PairDependentData = Pair<Int, Int>

open class ItemTimeEnableWheelAdapter(
    protected val valueEnabledProvider: WeakReference<ValueEnabledProvider>
) :
    BaseWheelPickerView.Adapter<TextWheelPickerView.Item, TextWheelViewHolder>() {
    interface ValueEnabledProvider {
        fun isEnabled(adapter: RecyclerView.Adapter<*>, valueIndex: Int): Boolean
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextWheelViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.wheel_picker_item, parent, false) as TextView
        return TextWheelViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextWheelViewHolder, position: Int) {
        val valueIndex = if (isCircular) position % values.count() else position
        val value =
            values.getOrNull(valueIndex) ?: return
        val isEnabled = valueEnabledProvider.get()?.isEnabled(this, valueIndex) ?: true
        holder.onBindData(
            TextWheelPickerView.Item(
                id = "$position",
                text = value.text,
                isEnabled = isEnabled
            )
        )
    }
}

/**
 * 3-column selector with cascading dependencies
 *
 * first column <- second column <- third column
 *
 * e.g. the year-month-day selector. Day depends on month, month depends on year.
 *
 * @property adapters Triple<Adapter<*>, Adapter<*>, Adapter<*>> adapters
 * @property currentData Triple<Int, Int, Int> current data
 * @constructor
 */
abstract class PairDependentPickerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), ItemTimeEnableWheelAdapter.ValueEnabledProvider {

    protected abstract val adapters: Pair<RecyclerView.Adapter<*>, RecyclerView.Adapter<*>>

    abstract val currentItem: PairDependentData

    protected abstract fun minData(): PairDependentData?
    protected abstract fun maxData(): PairDependentData?

    abstract fun value(adapter: RecyclerView.Adapter<*>, valueIndex: Int): Int

    protected fun reloadPickersIfNeeded(
        oldData: PairDependentData?,
        newData: PairDependentData?
    ) {
        if (oldData?.first != newData?.first) {
            adapters.first.notifyDataSetChanged()
        }
        val item = currentItem
        if (item.first == newData?.first &&
            (!(newData.first == oldData?.first && newData.second == oldData.second))
        ) {
            adapters.second.notifyDataSetChanged()
        }
    }

    protected abstract fun setFirst(value: Int, animated: Boolean, completion: (() -> Unit)?)

    protected abstract fun setSecond(
        value: Int,
        animated: Boolean,
        completion: (() -> Unit)?
    )

    protected fun updateCurrentDataByDataRangeIfNeeded(animated: Boolean): Boolean {
        minData()?.let {
            if (updateCurrentDataByMinData(it, animated)) {
                return true
            }
        }
        maxData()?.let {
            if (updateCurrentDataByMaxData(it, animated)) {
                return true
            }
        }
        return false
    }

    protected fun updateCurrentDataByMinData(
        minData: PairDependentData,
        animated: Boolean
    ): Boolean {
        setFirst(minData.first, animated, null)
        setSecond(minData.second, animated, null)

        return true
    }

    protected fun updateCurrentDataByMaxData(
        maxData: PairDependentData,
        animated: Boolean
    ): Boolean {
        setFirst(maxData.first, animated, null)
        setSecond(maxData.second, animated, null)

        return true
    }

    // region ItemTimeEnableWheelAdapter.ValueEnabledProvider
    override fun isEnabled(adapter: RecyclerView.Adapter<*>, valueIndex: Int): Boolean {
        val adapters = this.adapters
        val value = value(adapter, valueIndex)
        when (adapter) {
            adapters.first -> {
                minData()?.first?.let {
                    if (value < it) {
                        return false
                    }
                }
                maxData()?.first?.let {
                    if (value > it) {
                        return false
                    }
                }
            }
            adapters.second -> {
                val current = currentItem
                minData()?.let {
                    if (current.first > it.first) {
                        return true
                    }
                    if (value < it.second) {
                        return false
                    }
                }
                maxData()?.let {
                    if (current.first < it.first) {
                        return true
                    }
                    if (value > it.second) {
                        return false
                    }
                }
            }
        }
        return true
    }
    // endregion
}
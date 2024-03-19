package pet.perpet.equal.support.widget.picker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import pet.perpet.equal.databinding.PairPickerViewBinding
import pet.perpet.equal.support.widget.core.BaseWheelPickerView
import pet.perpet.equal.support.widget.core.ItemTimeEnableWheelAdapter
import pet.perpet.equal.support.widget.core.PairDependentData
import pet.perpet.equal.support.widget.core.PairDependentPickerView
import pet.perpet.equal.support.widget.core.TextWheelPickerView
import java.lang.ref.WeakReference


class TimePickerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : PairDependentPickerView(context, attrs, defStyleAttr),
    BaseWheelPickerView.WheelPickerViewListener {

    override val adapters: Pair<RecyclerView.Adapter<*>, RecyclerView.Adapter<*>>
        get() = Pair(typeAdapter, timeAdapter)

    override val currentItem: PairDependentData
        get() = PairDependentData(type, time)

    override fun minData(): PairDependentData? {
        if (minDate == null) {
            return null
        }
        return PairDependentData(
            0,
            1
        )

    }

    override fun maxData(): PairDependentData? {
        if (maxDate == null) {
            return null
        }
        return PairDependentData(
            1,
            11
        )
    }

    override fun value(adapter: RecyclerView.Adapter<*>, valueIndex: Int): Int {
        return valueIndex
    }

    interface Listener {
        fun didSelectData(type: Int, time: Int)

        fun scroll(state: Int?)
    }

    private val typePickerView: TextWheelPickerView
    private val timePickerView: TextWheelPickerView

    private var listener: Listener? = null

    fun setWheelListener(listener: Listener) {
        this.listener = listener
    }

    var minDate: Int? = null
        set(value) {
            val newValue =
                if (value != null && maxDate != null) minOf(maxDate ?: value, value) else value
            val oldData = minData()
            if (field == newValue) {
                return
            }
            field = newValue
            newValue?.let {
                it
            }
            val newData = minData()
            minData()?.let {
                updateCurrentDataByMinData(it, false)
            }
            reloadPickersIfNeeded(oldData, newData)
        }

    var maxDate: Int? = null
        set(value) {
            val newValue =
                if (value != null && minDate != null) maxOf(minDate ?: value, value) else value
            val oldData = maxData()
            if (field == newValue) {
                return
            }
            field = newValue
            newValue?.let {
               it
            }
            val newData = maxData()
            maxData()?.let {
                updateCurrentDataByMaxData(it, false)
            }
            reloadPickersIfNeeded(oldData, newData)
        }

    val time: Int
        get() = timePickerView.selectedIndex

    val type: Int
        get() = typePickerView.selectedIndex

    fun setDate(type: Int, time: Int) {
        setFirst(type, false) {
            setSecond(time, false) {
            }
        }
    }

    var isCircular: Boolean = false
        set(value) {
            field = value
            typePickerView.isCircular = value
            timePickerView.isCircular = value
        }

    private val typeAdapter = ItemTimeEnableWheelAdapter(WeakReference(this))
    private val timeAdapter = ItemTimeEnableWheelAdapter(WeakReference(this))

    private val binding: PairPickerViewBinding =
        PairPickerViewBinding.inflate(LayoutInflater.from(context), this)

    override fun setFirst(value: Int, animated: Boolean, completion: (() -> Unit)?) {
        if (this.type == value) {
            completion?.invoke()
            return
        }
        typePickerView.setSelectedIndex(value, animated, completion)
    }

    override fun setSecond(value: Int, animated: Boolean, completion: (() -> Unit)?) {
        if (this.time == value) {
            completion?.invoke()
            return
        }
        timePickerView.setSelectedIndex(value, animated, completion)
    }

    override fun setHapticFeedbackEnabled(hapticFeedbackEnabled: Boolean) {
        super.setHapticFeedbackEnabled(hapticFeedbackEnabled)
        typePickerView.isHapticFeedbackEnabled = hapticFeedbackEnabled
        timePickerView.isHapticFeedbackEnabled = hapticFeedbackEnabled
    }

    init {
        typePickerView = binding.leftPicker
        typePickerView.setAdapter(typeAdapter)
        timePickerView = binding.rightPicker
        timePickerView.setAdapter(timeAdapter)
        timeAdapter.values = (0 until 12).map {
            TextWheelPickerView.Item(
                "$it",
                "${it+1}시"
            )
        }
        typeAdapter.values = (0 until 2).map {
            TextWheelPickerView.Item(
                "$it",
                "${if(it == 0) "오전" else "오후"}"
            )
        }

        typePickerView.setWheelListener(this)
        timePickerView.setWheelListener(this)

        setDate(
            0,
           9
        )
    }

    private fun dateIsValid(data: PairDependentData): Boolean {
        if (data.first == RecyclerView.NO_POSITION && data.second == RecyclerView.NO_POSITION) {
            return false
        }
        val format = "%04d%"
        val selectedDateString = format.format(data.first, data.second)
        minData()?.let {
            if (selectedDateString < format.format(it.first, it.second)) {
                return false
            }
        }
        maxData()?.let {
            if (selectedDateString > format.format(it.first, it.second)) {
                return false
            }
        }
        return true
    }

    // region BaseWheelPickerView.WheelPickerViewListener
    override fun didSelectItem(picker: BaseWheelPickerView, index: Int) {
        if (minDate != null || maxDate != null) {
            if (picker == typePickerView) {
                typeAdapter.notifyDataSetChanged()
            } else if (picker == timePickerView) {
                timeAdapter.notifyDataSetChanged()
            }
        }
//        if (!dateIsValid(currentItem)) {
//            return
//        }
        listener?.didSelectData(type, time)
    }

    override fun onScrollStateChanged(state: Int) {
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            updateCurrentDataByDataRangeIfNeeded(true)
        }
    }

    override fun onTouchMove(state: Int?) {
        super.onTouchMove(state)
        listener?.scroll(state)
    }
    // endregion
}
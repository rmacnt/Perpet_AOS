package pet.perpet.framework.widget.calendarv2

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import pet.perpet.framework.R
import pet.perpet.framework.widget.calendarv2.data.Day
import pet.perpet.framework.widget.calendarv2.view.ExpandIconView
import pet.perpet.framework.widget.calendarv2.view.LockScrollView
import java.util.*


@Suppress("DEPRECATION", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@SuppressLint("ClickableViewAccessibility")
abstract class UICalendar constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : NestedScrollView(context, attrs, defStyleAttr) {

    constructor(context: Context) : this(context, null, 0)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    protected var mInflater: LayoutInflater = LayoutInflater.from(context)

    // UI
    protected var mLayoutRoot: LinearLayout
    protected var mTxtTitle: AppCompatTextView
    protected var mTableHead: TableLayout
    protected var mScrollViewBody: LockScrollView
    protected var mTableBody: TableLayout
    protected var mLayoutBtnGroupMonth: ConstraintLayout
    protected var mLayoutBtnGroupWeek: RelativeLayout
    protected var mBtnPrevMonth: AppCompatImageView
    protected var mBtnNextMonth: AppCompatImageView
    protected var mBtnPrevWeek: ImageView
    protected var mBtnNextWeek: ImageView
    protected var expandIconView: ExpandIconView
    var datePattern = "yyyy. MM"
        set(value: String) {
            field = value

        }
    // Attributes
    var isShowWeek = true
        set(showWeek) {
            field = showWeek

            if (showWeek) {
                mTableHead.visibility = View.VISIBLE
            } else {
                mTableHead.visibility = View.GONE
            }
        }
    var firstDayOfWeek = SUNDAY
        set(firstDayOfWeek) {
            field = firstDayOfWeek
            reload()
        }
    var hideArrow = true
        set(value: Boolean) {
            field = value
            hideButton()
        }
    open var state = STATE_EXPANDED
        set(state) {
            field = state

            if (this.state == STATE_EXPANDED) {
                mLayoutBtnGroupMonth.visibility = View.VISIBLE
                mLayoutBtnGroupWeek.visibility = View.GONE
            }
            if (this.state == STATE_COLLAPSED) {
                mLayoutBtnGroupMonth.visibility = View.VISIBLE
                mLayoutBtnGroupWeek.visibility = View.VISIBLE
            }
        }

    var textColor = Color.BLACK
        set(textColor) {
            field = textColor
            redraw()

            mTxtTitle.setTextColor(this.textColor)
        }
    var primaryColor = Color.WHITE
        set(primaryColor) {
            field = primaryColor
            redraw()

            mLayoutRoot.setBackgroundColor(this.primaryColor)
        }

    var todayItemTextColor = Color.BLACK
        set(todayItemTextColor) {
            field = todayItemTextColor
            redraw()
        }
    var todayItemBackgroundDrawable = resources.getDrawable(R.drawable.circle_bg)
        set(todayItemBackgroundDrawable) {
            field = todayItemBackgroundDrawable
            redraw()
        }
    var selectedItemTextColor = Color.WHITE
        set(selectedItemTextColor) {
            field = selectedItemTextColor
            redraw()
        }
    var selectedItemBackgroundDrawable: Drawable = resources.getDrawable(R.drawable.circle_bg)
        set(selectedItemBackground) {
            field = selectedItemBackground
            redraw()
        }

    /**
     * This can be used to defined the left icon drawable other than predefined icon
     */
    var buttonLeftDrawable: Drawable = resources.getDrawable(R.drawable.ic_calendar_right)
        set(buttonLeftDrawable) {
            field = buttonLeftDrawable
            mBtnPrevMonth.setImageDrawable(buttonLeftDrawable)
            mBtnPrevWeek.setImageDrawable(buttonLeftDrawable)
        }

    /**
     *  This can be used to set the drawable for the right icon, other than predefined icon
     */
    var buttonRightDrawable: Drawable = resources.getDrawable(R.drawable.ic_calendar_left)
        set(buttonRightDrawable) {
            field = buttonRightDrawable
            mBtnNextMonth.setImageDrawable(buttonRightDrawable)
            mBtnNextWeek.setImageDrawable(buttonRightDrawable)
        }

    var selectedItem: Day? = null

    private var mButtonLeftDrawableTintColor = Color.BLACK
    private var mButtonRightDrawableTintColor = Color.BLACK

    private var mExpandIconColor = Color.BLACK
    var eventColor = Color.BLACK
        private set(eventColor) {
            field = eventColor
            redraw()

        }


    fun getCurrentLocale(context: Context): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales.get(0)
        } else {

            context.resources.configuration.locale
        }
    }

    init {

        // load rootView from xml
        val rootView = mInflater.inflate(R.layout.widget_collapsible_calendarview, this, true)

        // init UI
        mLayoutRoot = rootView.findViewById(R.id.layout_root)
        mTxtTitle = rootView.findViewById(R.id.txt_title)
        mTableHead = rootView.findViewById(R.id.table_head)
        mTableBody = rootView.findViewById(R.id.table_body)
        mLayoutBtnGroupMonth = rootView.findViewById(R.id.layout_btn_group_month)
        mLayoutBtnGroupWeek = rootView.findViewById(R.id.layout_btn_group_week)
        mBtnPrevMonth = rootView.findViewById(R.id.btn_prev_month)
        mBtnNextMonth = rootView.findViewById(R.id.btn_next_month)
        mBtnPrevWeek = rootView.findViewById(R.id.btn_prev_week)
        mBtnNextWeek = rootView.findViewById(R.id.btn_next_week)
        mScrollViewBody = rootView.findViewById(R.id.scroll_view_body)
        expandIconView = rootView.findViewById(R.id.expandIcon)
        val attributes = context.theme.obtainStyledAttributes(
            attrs, R.styleable.UICalendar, defStyleAttr, 0)
        setAttributes(attributes)
        attributes.recycle()
    }

    protected abstract fun redraw()
    protected abstract fun reload()
    private fun hideButton() {
        mBtnNextWeek.visibility = View.GONE
        mBtnPrevWeek.visibility = View.GONE
        mBtnNextMonth.visibility = View.VISIBLE
        mBtnPrevMonth.visibility = View.VISIBLE
    }

    protected fun setAttributes(attrs: TypedArray) {
        // set attributes by the values from XML
        //setStyle(attrs.getInt(R.styleable.UICalendar_style, mStyle));
        isShowWeek = attrs.getBoolean(R.styleable.UICalendar_showWeek, isShowWeek)
        firstDayOfWeek = attrs.getInt(R.styleable.UICalendar_firstDayOfWeek, firstDayOfWeek)
        hideArrow = attrs.getBoolean(R.styleable.UICalendar_hideArrows, hideArrow)
        datePattern = attrs.getString(R.styleable.UICalendar_datePattern).let {
            it ?: datePattern
        }
        state = attrs.getInt(R.styleable.UICalendar_state, state)

        textColor = attrs.getColor(R.styleable.UICalendar_textColor_cal, textColor)
        primaryColor = attrs.getColor(R.styleable.UICalendar_primaryColor, primaryColor)

        eventColor = attrs.getColor(R.styleable.UICalendar_eventColor, eventColor)


        todayItemTextColor = attrs.getColor(R.styleable.UICalendar_todayItem_textColor, todayItemTextColor)
        var todayItemBackgroundDrawable = attrs.getDrawable(R.styleable.UICalendar_todayItem_background)
        if (todayItemBackgroundDrawable != null) {
            this.todayItemBackgroundDrawable = todayItemBackgroundDrawable
        } else {
            this.todayItemBackgroundDrawable = todayItemBackgroundDrawable
        }

        selectedItemTextColor = attrs.getColor(
            R.styleable.UICalendar_selectedItem_textColor, selectedItemTextColor)
        var selectedItemBackgroundDrawable = attrs.getDrawable(R.styleable.UICalendar_selectedItem_background)
        if (selectedItemBackgroundDrawable != null) {
            this.selectedItemBackgroundDrawable = selectedItemBackgroundDrawable
        }

        var buttonLeftDrawable = attrs.getDrawable(R.styleable.UICalendar_buttonLeft_drawable)
        buttonLeftDrawable ?: this.buttonLeftDrawable

        attrs.getDrawable(R.styleable.UICalendar_buttonRight_drawable)

        setButtonLeftDrawableTintColor(attrs.getColor(R.styleable.UICalendar_buttonLeft_drawableTintColor, mButtonLeftDrawableTintColor))
        setButtonRightDrawableTintColor(attrs.getColor(R.styleable.UICalendar_buttonRight_drawableTintColor, mButtonRightDrawableTintColor))
        setExpandIconColor(attrs.getColor(R.styleable.UICalendar_expandIconColor, mExpandIconColor))
    }

    fun setButtonLeftDrawableTintColor(color: Int) {
        this.mButtonLeftDrawableTintColor = color
//        mBtnPrevMonth.drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
//        mBtnPrevWeek.drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        redraw()
    }

    fun setButtonRightDrawableTintColor(color: Int) {

        this.mButtonRightDrawableTintColor = color
//        mBtnNextMonth.drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
//        mBtnNextWeek.drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        redraw()
    }

    fun setExpandIconColor(color: Int) {
        this.mExpandIconColor = color
        expandIconView.setColor(color)
    }

    abstract fun changeToToday()

    companion object {


        // Day of Week
        val SUNDAY = 0
        val MONDAY = 1
        val TUESDAY = 2
        val WEDNESDAY = 3
        val THURSDAY = 4
        val FRIDAY = 5
        val SATURDAY = 6
        // State
        val STATE_EXPANDED = 0
        val STATE_COLLAPSED = 1
        val STATE_PROCESSING = 2
    }


}
